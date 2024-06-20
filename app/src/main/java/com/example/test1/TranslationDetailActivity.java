package com.example.test1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TranslationDetailActivity extends BaseActivity {
    public static final String EXTRA_ORIGINAL_TEXT = "extra_original_text";
    public static final String EXTRA_TRANSLATED_TEXT = "extra_translated_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_detail);

        TextView originalTextView = findViewById(R.id.originalTextView);
        TextView translatedTextView = findViewById(R.id.translatedTextView);

        Intent intent = getIntent();
        if (intent != null) {
            String originalText = intent.getStringExtra(EXTRA_ORIGINAL_TEXT);
            String translatedText = intent.getStringExtra(EXTRA_TRANSLATED_TEXT);

            originalTextView.setText("原文："+originalText);
            translatedTextView.setText("译文："+translatedText);
        }
    }
}