package com.j.enjpery.app.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;

/**
 * Created by J on 2017/8/6.
 */
public class SaveImgUtil {

    private static final String TAG = SaveImgUtil.class.getSimpleName();
    public static final String IMGDIR = Environment.getExternalStorageDirectory().getPath() + "/weiSwift/weiSwift_img/";
    private Context mContext;

    private static SaveImgUtil sSaveUtil;

    private SaveImgUtil(Context context) {
        this.mContext = context;
    }

    public static SaveImgUtil create(Context context) {
        if (sSaveUtil == null) {
            sSaveUtil = new SaveImgUtil(context);
        }
        return sSaveUtil;
    }


    /**
     * @param imgFile 保存File文件到本地相册
     */
    public File saveImage(File imgFile, String filetype) {
        String destFilePath = IMGDIR + new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()) + filetype;

        final String targetPath = destFilePath.substring(0, destFilePath.lastIndexOf(File.separator) + 1);
        final String targetName = destFilePath.substring(destFilePath.lastIndexOf(File.separator) + 1);
        copyFileNio(imgFile.getAbsolutePath(), targetPath, targetName);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(destFilePath));
        intent.setData(uri);
        mContext.sendBroadcast(intent);
        Toast.makeText(mContext, "成功保存到相册！", Toast.LENGTH_SHORT).show();
        return new File(destFilePath);
    }


    public static void copyFileNio(String source, String targetPath, String targetName) {
        final File sourceFile = new File(source);
        if (!sourceFile.exists()) {
            return;
        }

        final File targetPathFile = new File(targetPath);
        if (!targetPathFile.exists()) {
            targetPathFile.mkdirs();
        }

        final File targeFile = new File(targetPath + targetName);
        if (!targeFile.exists()) {
            try {
                targeFile.createNewFile();
            } catch (IOException e) {

            }
        }

        copyFileNio(sourceFile, targeFile);
    }

    public static void copyFileNio(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(inStream);
            close(in);
            close(outStream);
            close(out);
        }
    }

    public static void close(InputStream inputStream) {
        try {
            if (null != inputStream) {
                inputStream.close();
            }
        } catch (IOException e) {
        }
    }

    public static void close(OutputStream outputStream) {
        try {
            if (null != outputStream) {
                outputStream.close();
            }
        } catch (IOException e) {
        }
    }

    public static void close(FileChannel fileChannel) {
        try {
            if (null != fileChannel) {
                fileChannel.close();
            }
        } catch (IOException e) {
        }
    }

}
