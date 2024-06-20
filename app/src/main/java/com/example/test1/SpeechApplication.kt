package com.example.test1

import android.app.Application
import com.iflytek.cloud.SpeechUtility


class SpeechApplication : Application() {

    override fun onCreate() {
        //   5ef048e1  为在开放平台注册的APPID  注意没有空格，直接替换即可
        SpeechUtility.createUtility(this@SpeechApplication, "appid=9e3ed5b1")

        super.onCreate()
    }
}