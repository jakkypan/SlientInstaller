package com.slientinstaller.demo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.slientinstaller.Util;
import com.slientinstaller.noroot.accessibility.SlientByAccessibilityInstall;
import com.slientinstaller.noroot.pm.SlientPmInstall;
import com.slientinstaller.root.SlientRootInstall;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        Util.copyAssetFile2SD(this, "test.apk");

        Button button = new Button(this);
        button.setText("root slient");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        final boolean result = SlientRootInstall.install(getFilesDir().getAbsolutePath() + "/test.apk");
                        final boolean result = SlientRootInstall.install(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.apk");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (result) {
                                    Toast.makeText(MainActivity.this, "安装成功！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "安装失败！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).start();
            }
        });
        layout.addView(button);

        Button button1 = new Button(this);
        button1.setText("reigster service");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlientByAccessibilityInstall.forwardToAccessibility(MainActivity.this);
            }
        });
        layout.addView(button1);

        Button button2 = new Button(this);
        button2.setText("accessibility install");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlientByAccessibilityInstall.accessibilityInstall(MainActivity.this, getFilesDir().getAbsolutePath() + "/test.apk");
            }
        });
        layout.addView(button2);

        Button button3 = new Button(this);
        button3.setText("pm install");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SlientPmInstall.install(MainActivity.this, getFilesDir().getAbsolutePath() + "/test.apk");
                SlientPmInstall.install(MainActivity.this, Environment.getExternalStorageDirectory() + "/test.apk");
            }
        });
        layout.addView(button3);

        setContentView(layout);
    }
}
