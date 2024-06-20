package com.example.test1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

public class IntentUtils {


    public static final String TYPE_IMAGE = "image/*";

    public static void shareImage(Context context, File file, String title) {
        shareFile(context, file, TYPE_IMAGE, title);
    }

    /**
     * 分享文件
     *
     * @param context
     * @param file
     * @param shareType
     * @param title
     */
    public static void shareFile(Context context, File file, String shareType, String title) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri =  FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            fileUri = Uri.fromFile(file);
        }
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.setType(shareType);
        Intent chooser = Intent.createChooser(intent, title);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(chooser);
        }
    }

    /**
     * 分享纯文本
     *
     * @param content 内容
     */
    public static void shareString(Activity activity, String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);//分享的文本内容
        sendIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(sendIntent, "分享到"));
    }

}


