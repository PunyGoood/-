package com.example.test1

import android.os.Bundle

import android.Manifest
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.iflytek.cloud.ErrorCode
import com.iflytek.cloud.InitListener
import com.iflytek.cloud.RecognizerResult
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechError
import com.iflytek.cloud.SpeechRecognizer
import com.iflytek.cloud.ui.RecognizerDialog
import com.iflytek.cloud.ui.RecognizerDialogListener
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.Random


class MicActivity : AppCompatActivity(), View.OnClickListener {
    var fromLanguage = "auto" //目标语言
    var toLanguage = "auto" //翻译语言

    val appId = "20240510002047632"
    val key = "7zz4uCk3NQPSShl5Fujo"

    private var mIat: SpeechRecognizer? = null // 语音听写对象
    private var mIatDialog: RecognizerDialog? = null // 语音听写UI

    // 用HashMap存储听写结果
    private val mIatResults: HashMap<String?, String> = LinkedHashMap()

    private var mSharedPreferences: SharedPreferences? = null //缓存

    private val mEngineType = SpeechConstant.TYPE_CLOUD // 引擎类型
    private val language = "zh_cn" //识别语言


    private var btnStart: ImageButton? = null //开始识别
    private val resultType = "json" //结果内容数据格式

    private lateinit var tvTranslation: TextView
    private lateinit var tvResult: TextView
    private lateinit var resultLay: LinearLayout
    private lateinit var tv_from: TextView // 声明 tv_from
    private lateinit var tv_to: TextView
    private lateinit var afterLay: LinearLayout
    var historyManager: TranslationHistoryManager? = null
    private var tvShare: TextView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mic)

        /*tvResult = findViewById(R.id.tv_result)*/
        btnStart = findViewById(R.id.btn_voice_input)
        btnStart?.setOnClickListener(this)
        tvTranslation = findViewById(R.id.tv__translation)
        resultLay = findViewById(R.id.result__lay)
        afterLay = findViewById(R.id.after__lay)
        tv_from = findViewById(R.id.tv__from)
        tv_to = findViewById(R.id.tv__to)
        tvResult = findViewById(R.id.tv__result)

        initPermission() //权限请求


        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this@MicActivity, mInitListener)
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = RecognizerDialog(this@MicActivity, mInitListener)
        mSharedPreferences = getSharedPreferences(
            "ASR",
            MODE_PRIVATE
        )
        historyManager = TranslationHistoryManager(this)
        tvShare = findViewById(R.id.tv_share)
        tvShare?.setOnClickListener { share(); }
    }

    private fun share() {
        IntentUtils.shareString(this, content);
    }

    override fun onClick(v: View) {
        if (null == mIat) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            showMsg("创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化")
            return
        }

        tvTranslation.setOnClickListener {
            transition()
        }

        mIatResults.clear() //清除数据
        setParam() // 设置参数
        mIatDialog!!.setListener(mRecognizerDialogListener) //设置监听
        mIatDialog!!.show() // 显示对话框
    }


    /**
     * 初始化监听器。
     */
    private val mInitListener = InitListener { code ->
        Log.d(TAG, "SpeechRecognizer init() code = $code")
        if (code != ErrorCode.SUCCESS) {
            showMsg("初始化失败，错误码：$code,请点击网址https://www.xfyun.cn/document/error-code查询解决方案")
        }
    }


    /**
     * 听写UI监听器
     */
    private val mRecognizerDialogListener: RecognizerDialogListener =
        object : RecognizerDialogListener {
            override fun onResult(results: RecognizerResult, isLast: Boolean) {
                printResult(results) //结果数据解析
            }

            /**
             * 识别回调错误.
             */
            override fun onError(error: SpeechError) {
                showMsg(error.getPlainDescription(true))
            }
        }

    /**
     * 数据解析
     *
     * @param results
     */
    private fun printResult(results: RecognizerResult) {
        val text: String = JsonParser.parseIatResult(results.resultString)

        var sn: String? = null
        // 读取json结果中的sn字段
        try {
            val resultJson = JSONObject(results.resultString)
            sn = resultJson.optString("sn")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        mIatResults[sn] = text


    }
    var inputTx="";
    private fun transition() {

        val resultBuffer = StringBuffer()
        for (key in mIatResults.keys) {
            resultBuffer.append(mIatResults[key])
        }

        // 获取输入的内容
        inputTx = resultBuffer.toString().trim() // 将 Editable 转换为 String 然后 trim
        // 判断输入内容是否为空
        if (inputTx.isNotEmpty()) { // 检查字符串非空
            tvTranslation.text = "翻译中..."
            tvTranslation.isEnabled = false // 不可更改，无法点击

            // 调用 num 函数生成随机数，确保 num 函数返回的是 Long 类型
            val salt = num(1) // 随机数

            // 拼接一个字符串然后加密
            val spliceStr = "$appId$inputTx$salt$key" // 使用 Kotlin 的字符串模板拼接字符串

            // 将拼接好的字符串进行小写的 MD5 加密
            val sign = toMD5(spliceStr)

            // 异步 Get 请求访问网络
            asyncGet(inputTx, fromLanguage, toLanguage, salt.toString(), sign)
        } else {
            // 处理空输入的情况，例如显示一个提示
            /*tvTranslation.text = "请输入要翻译的内容"*/
        }
    }


    /**
     * 参数设置
     *
     * @return
     */
    fun setParam() {
        // 清空参数
        mIat!!.setParameter(SpeechConstant.PARAMS, null)
        // 设置听写引擎
        mIat!!.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType)
        // 设置返回结果格式
        mIat!!.setParameter(SpeechConstant.RESULT_TYPE, resultType)

        if (language == "zh_cn") {
            val lag = mSharedPreferences!!.getString(
                "iat_language_preference",
                "mandarin"
            )
            Log.e(TAG, "language:$language") // 设置语言
            mIat!!.setParameter(SpeechConstant.LANGUAGE, "zh_cn")
            // 设置语言区域
            mIat!!.setParameter(SpeechConstant.ACCENT, lag)
        } else {
            mIat!!.setParameter(SpeechConstant.LANGUAGE, language)
        }
        Log.e(TAG, "last language:" + mIat!!.getParameter(SpeechConstant.LANGUAGE))

        //此处用于设置dialog中不显示错误码信息
        //mIat.setParameter("view_tips_plain","false");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat!!.setParameter(
            SpeechConstant.VAD_BOS,
            mSharedPreferences!!.getString("iat_vadbos_preference", "4000")
        )

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat!!.setParameter(
            SpeechConstant.VAD_EOS,
            mSharedPreferences!!.getString("iat_vadeos_preference", "1000")
        )

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat!!.setParameter(
            SpeechConstant.ASR_PTT,
            mSharedPreferences!!.getString("iat_punc_preference", "1")
        )

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mIat!!.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
        mIat!!.setParameter(
            SpeechConstant.ASR_AUDIO_PATH,
            Environment.getExternalStorageDirectory().toString() + "/msc/iat.wav"
        )
    }


    /**
     * 提示消息
     * @param msg
     */
    private fun showMsg(msg: String) {
        Toast.makeText(this@MicActivity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (null != mIat) {
            // 退出时释放连接
            mIat!!.cancel()
            mIat!!.destroy()
        }
    }


    /**
     * android 6.0 以上需要动态申请权限
     */
    private fun initPermission() {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val toApplyList = ArrayList<String>()

        for (perm in permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(
                    this,
                    perm
                )
            ) {
                toApplyList.add(perm)
            }
        }
        val tmpList = arrayOfNulls<String>(toApplyList.size)
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123)
        }
    }

    /**
     * 权限申请回调，可以作进一步处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }


    companion object {
        private const val TAG = "MicActivity"
    }

    private fun initAfter(from: String?, to: String?) {
        when (from) {
            "zh" -> tv_from.text = "中文"
            "en" -> tv_from.text = "英文"

        }
        when (to) {
            "zh" -> tv_to.text = "中文"
            "en" -> tv_to.text = "英文"

        }
    }


    private fun num(a: Int): Long {
        val random = Random(a.toLong())
        var ran1: Long = 0
        for (i in 0 until 5) { // 使用 until 来确保循环次数为 5 次
            // 通过取模操作生成一个 0 到 99 之间的随机数
            ran1 += random.nextLong() % 100
        }
        return ran1
    }

    private fun asyncGet(
        content: String,
        fromType: String,
        toType: String,
        salt: String,
        sign: String?
    ) {


        val httpStr = "http://api.fanyi.baidu.com/api/trans/vip/translate"
        val httpsStr = "https://fanyi-api.baidu.com/api/trans/vip/translate"
        //拼接请求的地址  Kotlin运行在字符串中直接拼接
        val url = httpsStr +
                "?appid=$appId&q=$content&from=$fromType&to=$toType&salt=$salt&sign=$sign"
        val okHttpClient = OkHttpClient()
        val request = Request.Builder().url(url).get().build()
        val call: Call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //异常返回
                goToUIThread(e.toString(), 0)
            }

            override fun onResponse(call: Call, response: Response) {
                //正常返回
                goToUIThread(response.body!!.string(), 1)
            }

        })
    }

    var content = "";
    private fun goToUIThread(any: Any, key: Int) {
        //切换到主线程处理数据
        runOnUiThread {
            tvTranslation.text = "翻译"
            tvTranslation.isEnabled = true

            if (key == 0) {//异常返回
                showMsg("异常信息：$any")
                Log.e("MainActivity", any.toString())
            } else {//正常返回
                //通过Gson 将 JSON字符串转为实体Bean
                val (from, to, trans_result) = Gson().fromJson<TranslateResult>(
                    any.toString(),
                    TranslateResult::class.java
                )
                tvTranslation.visibility = View.GONE
                //显示翻译的结果
                tvResult.text = trans_result[0].dst
                resultLay.visibility = View.VISIBLE
                /*beforeLay.visibility = View.GONE*/
                afterLay.visibility = View.VISIBLE

                tvShare?.visibility = View.VISIBLE//显示分享按钮
                historyManager!!.saveTranslation(inputTx, trans_result[0].dst)
                content = "原文:"+trans_result[0].src+" 译文:"+ trans_result[0].dst
                //翻译成功后的语言判断显示
                initAfter(from, to)
            }
        }
    }

}
