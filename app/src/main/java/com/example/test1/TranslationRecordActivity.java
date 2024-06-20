package com.example.test1;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import com.example.test1.KeyboardActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TranslationRecordActivity extends BaseActivity {
    private TranslationHistoryManager historyManager;
    private EditText editText;
    private ListView historyListView;
    private TranslationAdapter historyAdapter;

    private String textToTranslate;//原文
    private String translatedText;//译文
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_record);




        historyManager = new TranslationHistoryManager(this);

        historyListView = findViewById(R.id.historyListView);

        // 设置历史记录列表
        List<String> historyKeys = historyManager.getHistoryKeys();
        historyAdapter = new TranslationAdapter(this, historyKeys, historyManager);
        historyListView.setAdapter(historyAdapter);
    }

    private final View.OnClickListener translateButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            if (!textToTranslate.isEmpty()) {

                historyManager.saveTranslation(textToTranslate, translatedText);

                // 更新历史记录列表
                historyAdapter.clear();
                historyAdapter.addAll(historyManager.getHistoryKeys());
                historyAdapter.notifyDataSetChanged();
            }
        }
    };


}