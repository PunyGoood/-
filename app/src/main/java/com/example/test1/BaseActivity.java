package com.example.test1;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 获取主题更换状态
        final SharedPreferences sharedPreferences = getSharedPreferences("useMyTheme", MODE_PRIVATE);
        boolean useMyTheme = sharedPreferences.getBoolean("useMyTheme", true);  // 第一次默认设置为亮色主题

        // 设置主题
        if (!useMyTheme) {
            setTheme(R.style.NightTheme);  // 黑色主题
        } else {
            setTheme(R.style.LightTheme);  // 亮色主题
        }

        super.onCreate(savedInstanceState);
    }
}