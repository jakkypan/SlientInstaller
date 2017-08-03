package com.slientinstaller.noroot.accessibility;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import java.io.File;

/**
 * 只支持4.1+的系统
 * <p>
 * 借助android系统自身的无障碍功能完成非root下的静默安装。
 * <p>
 * </p>
 * <p>
 * 但是这种静默安装首先需要用户开启无障碍的服务，同时用户是会看到安装的界面的。所以并不是真正意义上的静默，只是由系统来模拟了人的行为。
 * <p>
 * </p>
 * <p>
 * 详细的餐考：https://developer.android.com/guide/topics/ui/accessibility/services.html
 * <p>
 * </p>
 * <p>
 * ps：可以将这种自动化的操作应用到自己的app上，只要将android:packageNames指定为自己的就好了。
 * <p>
 * <p>
 * Created by panda on 2017/8/3.
 */
public class SlientByAccessibilityInstall {

    public static void forwardToAccessibility(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        activity.startActivity(intent);
    }

    public static void accessibilityInstall(Activity activity, String apkPath) {
        Uri uri = Uri.fromFile(new File(apkPath));
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        activity.startActivity(localIntent);

//        Intent intent = activity.getPackageManager().getLaunchIntentForPackage("com.xrouter.demo");
//        activity.startActivity(intent);
    }
}
