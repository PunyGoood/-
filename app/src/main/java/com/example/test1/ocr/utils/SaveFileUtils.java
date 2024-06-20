package com.example.test1.ocr.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SaveFileUtils {

    /**
     * 把bitmap保存成file
     *
     * @param bitmap
     * @param path
     */
    public static String saveBitmapToFile(Context context,Bitmap bitmap) {
        String path=context.getExternalFilesDir("img")+File.separator+"test.jpg";//获取本地目录
        File file = new File(path);
        if (bitmap != null) {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getPath();
    }

    /**
     * 根据uri保存
     *
     * @param context
     * @param srcUri
     */
    public static String getPath(Context context, Uri srcUri) {
        String path=context.getExternalFilesDir("img")+File.separator+"test.jpg";//获取本地目录
        File file=new File(path);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);//context的方法获取URI文件输入流
            if (inputStream == null) return "null";
            OutputStream outputStream = new FileOutputStream(file);
            copyStream(inputStream, outputStream);//调用下面的方法存储
            inputStream.close();
            outputStream.close();
            return path;//成功返回路径
        } catch (Exception e) {
            e.printStackTrace();
            return "null";//失败返回路径null
        }
    }

    private static void copyStream(InputStream input, OutputStream output){//文件存储
        final int BUFFER_SIZE = 1024 * 2;
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
