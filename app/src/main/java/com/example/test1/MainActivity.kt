package com.example.test1

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : BaseActivity() {

    private val beforeLay: LinearLayout? = null //翻译之前的布局

    /*private val spLanguage: NiceSpinner? = null //语言选择下拉框*/

    private val afterLay: LinearLayout? = null //翻译之后的布局

    private val tvFrom: TextView? = null //翻译源语言

    private val tvTo: TextView? = null //翻译目标语言


    private val edContent: EditText? = null //输入框（要翻译的内容）

    private val ivClearTx: ImageView? = null //清空输入框按钮

    private val tvTranslation: TextView? = null //翻译


    private val resultLay: LinearLayout? = null //翻译结果布局

    private val tvResult: TextView? = null //翻译的结果

    private val ivCopyTx: ImageView? = null //复制翻译的结果


    private val fromLanguage = "auto" //目标语言

    private val toLanguage = "auto" //翻译语言


    /*private val myClipboard: ClipboardManager? = null //复制文本*/

    private val myClip: ClipData? = null //剪辑数据


    private val appId = "20201125000625305" //APP ID 来源于百度翻译平台 请使用自己的

    private val key = "6vjmDnNxypmebgbzKxul" //秘钥 来源于百度翻译平台 请使用自己的





    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 获取布局中的按钮并设置点击监听器
        val buttonClock: Button = findViewById(R.id.button_clock)
        val buttonSettings: Button = findViewById(R.id.button_settings)
        val buttonMic: Button = findViewById(R.id.button_mic)
        val buttonKeyboard: Button = findViewById(R.id.button_keyboard)
        val buttonImage: Button = findViewById(R.id.button_image)

        // 历史记录按钮的点击事件
        buttonClock.setOnClickListener {
            val clockIntent = Intent(this, TranslationRecordActivity::class.java)
            startActivity(clockIntent)
        }

        // 设置按钮的点击事件
        buttonSettings.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }

        // 话筒按钮的点击事件
        buttonMic.setOnClickListener {
            val micIntent = Intent(this, MicActivity::class.java)
            startActivity(micIntent)
        }

        // 文本按钮的点击事件

        buttonKeyboard.setOnClickListener {
            val keyboardIntent = Intent(this, KeyboardActivity::class.java)
            startActivity(keyboardIntent)
        }

        // 图片按钮的点击事件
        buttonImage.setOnClickListener {
            val imageIntent = Intent(this, ImageTransActivity::class.java)
            startActivity(imageIntent)
        }
    }

    companion object {
        private const val TIME_INTERVAL = 2000L // 时间间隔，单位为毫秒
    }

    private var mBackPressed: Long = 0

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity() // 结束当前Activity及其所有祖先栈上的Activity
        } else {
            Toast.makeText(this, "再按一次返回退出程序", Toast.LENGTH_SHORT).show()
        }
        mBackPressed = System.currentTimeMillis()
    }



}







