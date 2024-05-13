package com.example.test1

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

/*import org.angmarch.views.NiceSpinner
import kotlinx.android.synthetic.main.activity_main.**/
import okhttp3.*
import java.util.*

import java.io.IOException
import android.widget.ArrayAdapter
class KeyboardActivity() : AppCompatActivity(), Parcelable {

    var fromLanguage = "auto" //目标语言
    var toLanguage = "auto" //翻译语言

    val appId = "20240510002047632"
    val key ="7zz4uCk3NQPSShl5Fujo"

    private val data: List<String> = LinkedList(
        listOf(
            "自动检测语言", "中文 → 英文", "英文 → 中文",
            "中文 → 繁体中文", "中文 → 粤语", "中文 → 日语",
            "中文 → 韩语", "中文 → 法语", "中文 → 俄语",
            "中文 → 阿拉伯语", "中文 → 西班牙语 ", "中文 → 意大利语"
        )
    )
    
    private lateinit var ivClearTx: ImageView
    private lateinit var ivCopyTx: ImageView // 声明 iv_copy_tx
    private lateinit var tvTranslation: TextView // 声明 tv_translation
    private lateinit var edContent: EditText // 声明 ed_content
    private lateinit var tvResult: TextView // 声明 tv_result
    private lateinit var myClipboard: ClipboardManager
    private lateinit var spLanguage: Spinner // 声明 sp_language
    private lateinit var resultLay: LinearLayout // 声明 result_lay
    private lateinit var beforeLay: LinearLayout // 声明 before_lay
    private lateinit var afterLay: LinearLayout // 声明 after_lay


    private lateinit var tv_from: TextView // 声明 tv_from
    private lateinit var tv_to: TextView
    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyboard)

        // 使用 findViewById 初始化所有视图
        ivClearTx = findViewById(R.id.iv_clear_tx)
        ivCopyTx = findViewById(R.id.iv_copy_tx)
        tvTranslation = findViewById(R.id.tv_translation)
        edContent = findViewById(R.id.ed_content)
        tvResult = findViewById(R.id.tv_result)
        spLanguage = findViewById(R.id.sp_language)
        resultLay = findViewById(R.id.result_lay)
        beforeLay = findViewById(R.id.before_lay)
        afterLay = findViewById(R.id.after_lay)

        tv_from = findViewById(R.id.tv_from)
        tv_to = findViewById(R.id.tv_to)
        myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        onClick()


        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // 设置下拉列表项的布局
        spLanguage.adapter = spinnerAdapter

        /*spLanguage = findViewById(R.id.sp_language)
        spLanguage.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)*/

        editTextListener()
        spinnerListener()
    }

    private fun onClick() {
        ivClearTx.setOnClickListener {
            edContent.text.clear()
        }

        ivCopyTx.setOnClickListener {
            val result = tvResult.text.toString()
            myClipboard.setPrimaryClip(ClipData.newPlainText("text", result))
            showMsg("已复制")
        }

        tvTranslation.setOnClickListener {
            transition()
        }
    }

    private fun editTextListener() {
        edContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ivClearTx.visibility = View.VISIBLE
                val content = edContent.text.toString().trim()
                if (content.isEmpty()) {
                    resultLay.visibility = View.GONE
                    tvTranslation.visibility = View.VISIBLE
                    beforeLay.visibility = View.VISIBLE
                    afterLay.visibility = View.GONE
                    ivClearTx.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
    }

    private fun spinnerListener() {
        spLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {//自动监测
                        fromLanguage = "auto";toLanguage = fromLanguage
                    }
                    1 -> {//中文 → 英文
                        fromLanguage = "zh";toLanguage = "en"
                    }
                    2 -> {//英文 → 中文
                        fromLanguage = "en";toLanguage = "zh"
                    }
                    3 -> {//中文 → 繁体中文
                        fromLanguage = "zh";toLanguage = "cht"
                    }
                    4 -> {//中文 → 粤语
                        fromLanguage = "zh";toLanguage = "cht"
                    }
                    5 -> {//中文 → 日语
                        fromLanguage = "zh";toLanguage = "jp"
                    }
                    6 -> {//中文 → 韩语
                        fromLanguage = "zh";toLanguage = "kor"
                    }
                    7 -> {//中文 → 法语
                        fromLanguage = "zh";toLanguage = "fra"
                    }
                    8 -> {//中文 → 俄语
                        fromLanguage = "zh";toLanguage = "ru"
                    }
                    9 -> {//中文 → 阿拉伯语
                        fromLanguage = "zh";toLanguage = "ara"
                    }
                    10 -> {//中文 → 西班牙语
                        fromLanguage = "zh";toLanguage = "spa"
                    }
                    11 -> {//中文 → 意大利语
                        fromLanguage = "zh";toLanguage = "it"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Spinner 没有选中任何项时的处理
            }
        }
    }

    private fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun transition() {
        // 获取输入的内容
        val inputTx = edContent.text.toString().trim() // 将 Editable 转换为 String 然后 trim
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
            tvTranslation.text = "请输入要翻译的内容"
        }
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
                goToUIThread(response.body()!!.string(), 1)
            }

        })
    }

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
                beforeLay.visibility = View.GONE
                afterLay.visibility = View.VISIBLE
                //翻译成功后的语言判断显示
                initAfter(from, to)
            }
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
    private fun initAfter(from: String?, to: String?) {
        when (from) {
            "zh" -> tv_from.text = "中文"
            "en" -> tv_from.text = "英文"
            "yue" -> tv_from.text = "粤语"
            "cht" -> tv_from.text = "繁体中文"
            "jp" -> tv_from.text = "日语"
            "kor" -> tv_from.text = "韩语"
            "fra" -> tv_from.text = "法语"
            "ru" -> tv_from.text = "俄语"
            "ara" -> tv_from.text = "阿拉伯语"
            "spa" -> tv_from.text = "西班牙语"
            "it" -> tv_from.text = "意大利语"
        }
        when (to) {
            "zh" -> tv_to.text = "中文"
            "en" -> tv_to.text = "英文"
            "yue" -> tv_to.text = "粤语"
            "cht" -> tv_to.text = "繁体中文"
            "jp" -> tv_to.text = "日语"
            "kor" -> tv_to.text = "韩语"
            "fra" -> tv_to.text = "法语"
            "ru" -> tv_to.text = "俄语"
            "ara" -> tv_to.text = "阿拉伯语"
            "spa" -> tv_to.text = "西班牙语"
            "it" -> tv_to.text = "意大利语"
        }
    }
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        // 实现如何将 KeyboardActivity 的状态写入 Parcel
        // 例如：
        parcel.writeString(fromLanguage)
        parcel.writeString(toLanguage)
        // 其他需要保存的状态...
    }



    companion object CREATOR : Parcelable.Creator<KeyboardActivity> {
        override fun createFromParcel(parcel: Parcel): KeyboardActivity {
            return KeyboardActivity(parcel)
        }

        override fun newArray(size: Int): Array<KeyboardActivity?> {
            return arrayOfNulls(size)
        }
    }
}