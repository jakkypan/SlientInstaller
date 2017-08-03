package com.slientinstaller.noroot.pm;

import android.content.Context;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.IPackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 直接绕过安装界面，调用PM的安装方法
 * <p>
 * app进程和系统app进程是同一个进程，这个设置 android:sharedUserId="android.uid.system"即可
 * <p>
 * 获得PackageManager的installPackage方法，由于API隐藏和重写，已经很难找到对应的installPackage,目前找到的是installPackage(String,String,String,String)，具体参数未知，因此我们需要使用frameworks层的相关代码，获得IPackageManager，并且通过反射获得ServiceManager，实现IPackageInstallObserver相关方法
 * <p>
 * 使用signApk+platform.x509.pem+platform.pk8对app进行签名，使其具有系统签名
 * <p>
 * Created by panda on 2017/8/3.
 */
public class SlientPmInstall {
    /****表示安装时以更新方式安装，即app不存在时安装，否则进行卸载再安装****/
    private static final int INSTALL_REPLACE_EXISTING = 0x00000002;

    public static void install(Context context, String apkFile) {
        try {
            File file = new File(apkFile);
            Uri uri = Uri.fromFile(new File(apkFile));
            Class<?> clazz = Class.forName("android.os.ServiceManager");
            Method method = clazz.getMethod("getService", String.class);
            IBinder iBinder = (IBinder) method.invoke(null, "package");
            IPackageManager ipm = IPackageManager.Stub.asInterface(iBinder);
            ipm.installPackage(uri, new PackageInstallObserver(context), INSTALL_REPLACE_EXISTING, file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 观察者，用户知道安装的结果
     */
    static class PackageInstallObserver extends IPackageInstallObserver.Stub {
        Context context;

        public PackageInstallObserver(Context context) {
            this.context = context;
        }

        @Override
        public void packageInstalled(String packageName, int returnCode) throws RemoteException {
            if (returnCode == 1) //返回1表示安装成功，否则安装失败
            {
                Toast.makeText(context, "安装成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "安装失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    ;
}
