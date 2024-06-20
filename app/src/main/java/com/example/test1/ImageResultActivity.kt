package com.example.test1

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.test1.ocr.utils.ScreenUtils
import java.io.File

// TODO: 图片翻译展示
class ImageResultActivity : BaseActivity() {

    private var ivShow: ImageView? = null
    private var path: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_result)
        ivShow = findViewById(R.id.iv_show)

        val btnShare = findViewById<Button>(R.id.btn_share)
        btnShare.setOnClickListener {
            if (!File(path).exists()) {
                Toast.makeText(this, "图片不存在", Toast.LENGTH_SHORT).show()
            } else {
                IntentUtils.shareImage(this, File(path), "分享图片")
            }
        }

        path = intent.getStringExtra("path")
        showImage(path!!)
    }

    private fun showImage(path: String) {
        val lp = ivShow!!.layoutParams
        val bitmap = BitmapFactory.decodeFile(path)
        //获取图片宽高
        val height = bitmap.height.toDouble()
        val width = bitmap.width.toDouble()

        if (height > width) {
            lp.height = ScreenUtils.getInstance().dip2px(this, 360f)
            lp.width = (width / height * lp.height).toInt()
        } else {
            lp.width = ScreenUtils.getInstance().dip2px(this, 360f)
            lp.height = (height / width * lp.width).toInt()
        }
        ivShow!!.layoutParams = lp
        ivShow!!.setImageBitmap(bitmap)
    }

}