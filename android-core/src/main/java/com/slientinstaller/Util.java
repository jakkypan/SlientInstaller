package com.slientinstaller;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by panda on 2017/8/3.
 */
public class Util {
    /**
     * 写入到内部存储
     *
     * @param context
     * @param assetFileName
     * @return
     */
    public static boolean copyAssetFile2SD(Context context, String assetFileName) {
        boolean isSuccess = false;
        File destFile = new File(context.getFilesDir(), assetFileName);

        InputStream assetInput = null;
        FileOutputStream destOutput = null;
        try {
            assetInput = context.getAssets().open(assetFileName);
            destOutput = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = assetInput.read(buffer)) != -1) {
                destOutput.write(buffer, 0, byteCount);
            }
            destOutput.flush();
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (assetInput != null) {
                try {
                    assetInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (destOutput != null) {
                try {
                    destOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }
}
