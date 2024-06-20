package com.example.test1.ocr.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/*
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　 ████━████     ┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　 　 ┗━━━┓
 * 　　　　　　　　　┃ 神兽保佑　　 ┣┓
 * 　　　　　　　　　┃ 代码无BUG   ┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 *  图片转base64
 * @author by ling
 *  from：
 * @time 2022/12/26 12:01
 */
public class Base64Helper {

    private static Base64Helper base64Helper;
    private Context context;

    public static Base64Helper getInstance(Context context) {
        if (base64Helper == null) {
            synchronized (Base64Helper.class){
                if(base64Helper == null){
                    base64Helper = new Base64Helper(context);
                }
            }
        }
        return base64Helper;
    }

    public Base64Helper(Context context) {
        this.context = context;
    }

    /**
     * base64转图片存储在本地
     *
     * @param imgBase64
     * @param imgPath
     * @param split     //data:image/png;base64格式开头
     */
    public boolean getImgBase64ToPNG(String imgBase64, String imgPath, boolean split) {
        byte[] decode = new byte[0];
        if (split) {
            decode = android.util.Base64.decode(imgBase64.split(",")[1], android.util.Base64.DEFAULT);
        } else {
            decode = android.util.Base64.decode(imgBase64, android.util.Base64.DEFAULT);
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        File file = new File(imgPath);
        if (bitmap != null) {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


}