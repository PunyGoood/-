package com.example.test1


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSON
import com.example.test1.ocr.bean.ReturnData
import com.example.test1.ocr.data.Config
import com.example.test1.ocr.data.Language
import com.example.test1.ocr.http.HttpStringCallback
import com.example.test1.ocr.pic.PicTranslate
import com.example.test1.ocr.utils.Base64Helper
import com.example.test1.ocr.utils.Constant
import com.example.test1.ocr.utils.Permission
import com.example.test1.ocr.utils.SaveFileUtils
import java.io.File

// TODO: 图片翻译
class ImageTransActivity : BaseActivity() {

    private var ivShow: ImageView? = null
    private var path: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val btnChoose = findViewById<Button>(R.id.btn_choose)
        btnChoose.setOnClickListener {
            selectImage()
        }

        val btnTake = findViewById<Button>(R.id.btn_take_photo)
        btnTake.setOnClickListener {
            if (Permission.isPermissionGranted(this)) {
                take()
            } else {
                Permission.checkPermission(this);
            }
        }
        ivShow = findViewById<ImageView>(R.id.iv_photo)
        path = getExternalFilesDir("img").toString() + File.separator + "test.jpg"
    }

    private fun take() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 2000)
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.setType("image/*")
        startActivityForResult(intent, 1000)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1000 && resultCode == RESULT_OK && null != data) {
            val uri = data.data
            val path: String = SaveFileUtils.getPath(this, uri)
            if (File(path).exists()) {
                request(path)
            }
        }
        if (requestCode == 2000 && resultCode == RESULT_OK && null != data) {
            val bitmap = data.extras!!["data"] as Bitmap?
            val save: String = SaveFileUtils.saveBitmapToFile(this, bitmap)
            if (File(save).exists()) {
                request(path!!)
            }
        }
    }

    private fun request(path: String) {
        Toast.makeText(this, "正在识别中...", Toast.LENGTH_SHORT).show()
        val config: Config = Config(Constant.APP_ID, Constant.SECRET_KEY)
        config.lang(Language.ZH, Language.EN)
        config.pic(path)
        config.erase(Config.ERASE_NONE)
        config.paste(Config.PASTE_FULL)

        val picTranslate: PicTranslate = PicTranslate()
        picTranslate.setConfig(config)

        picTranslate.trans(object : HttpStringCallback() {
            override fun onSuccess(response: String?) {
                super.onSuccess(response)
                if (!TextUtils.isEmpty(response)) {
                    // Gson gson = new Gson();//Gson转换有问题
                    //ReturnData data = gson.fromJson(response, ReturnData.class);

                    val data: ReturnData =
                        JSON.parseObject(response, ReturnData::class.java) //替换为fastjson
                    if (data.getErrorCode().equals("0")) { //请求成功
                        val save: Boolean =
                            Base64Helper.getInstance(this@ImageTransActivity).getImgBase64ToPNG(
                                data.getData().getPasteImg(),
                                path, false
                            )
                        if (save) {
                            handler.sendEmptyMessageDelayed(2, 1000)
                        }
                    } else {
                        Log.e("ling", "错误码: " + data.getErrorCode())
                        handler.sendEmptyMessage(1)
                    }
                }
            }

            override fun onFailure(e: Throwable) {
                super.onFailure(e)
                Log.e("ling", "错误码: " + e.message)
                handler.sendEmptyMessage(1)
            }
        })
    }

    var handler: Handler = Handler { msg: Message ->
        when (msg.what) {
            1 -> Toast.makeText(this, "请求异常", Toast.LENGTH_SHORT)
            2 -> start(path!!)
        }
        false
    }

    private fun start(path: String) {
        val intent = Intent(
            this,
            ImageResultActivity::class.java
        )
        intent.putExtra("path", path)
        startActivity(intent)
    }


}
