package com.example.myapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.test1.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 获取布局中的按钮并设置点击监听器
        val buttonClock: Button = findViewById(R.id.button_clock)
        val buttonSettings: Button = findViewById(R.id.button_settings)
        val buttonMic: Button = findViewById(R.id.button_mic)
        val buttonKeyboard: Button = findViewById(R.id.button_keyboard)
        val buttonImage: Button = findViewById(R.id.button_image)


        buttonClock.setOnClickListener {
            // 时钟事件
        }

        buttonSettings.setOnClickListener {
            // 设置事件
        }

        buttonMic.setOnClickListener {
            // 话筒事件
        }

        buttonKeyboard.setOnClickListener {
            // 键盘事件
        }

        buttonImage.setOnClickListener {
            // 图片事件
        }
    }
}