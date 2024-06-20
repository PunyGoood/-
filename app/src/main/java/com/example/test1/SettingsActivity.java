package com.example.test1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class SettingsActivity extends AppCompatActivity {

    String content = "圣人翻译app下载地址：https://github.com/PunyGoood/-";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        // Initialize the "儿童保护指南" button
        Button sharebutton = findViewById(R.id.shareButton);
        Button childProtectionGuideButton = findViewById(R.id.childProtectionGuideButton);
        Button yinsiButton = findViewById(R.id.yinsi);
        Button aboutusButton = findViewById(R.id.aboutus);
        sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
        childProtectionGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ChildProtectionActivity.class);
                startActivity(intent);
            }
        });

        yinsiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, yinsi.class);
                startActivity(intent);
            }
        });

        aboutusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, aboutus.class);
                startActivity(intent);
            }
        });


    }
    private void share() {
        com.example.test1.IntentUtils.shareString(this, content);
    }
}
