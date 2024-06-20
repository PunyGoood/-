package com.example.test1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class TranslationAdapter extends ArrayAdapter<String> {
    private TranslationHistoryManager historyManager;
    private List<String> translations;

    public TranslationAdapter(Context context, List<String> translations, TranslationHistoryManager historyManager) {
        super(context, 0, translations);
        this.historyManager = historyManager;
        this.translations = translations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_translation, parent, false);
        }

        String originalText = getItem(position);
        TextView originalTextView = convertView.findViewById(R.id.originalTextView);
        originalTextView.setText(originalText);

        // 设置点击事件跳转到详情页面
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String translatedText = historyManager.getTranslation(originalText);
                if (translatedText != null) {
                    Intent intent = new Intent(getContext(), TranslationDetailActivity.class);
                    intent.putExtra(TranslationDetailActivity.EXTRA_ORIGINAL_TEXT, originalText);
                    intent.putExtra(TranslationDetailActivity.EXTRA_TRANSLATED_TEXT, translatedText);
                    getContext().startActivity(intent);
                }
            }
        });

        // 设置删除按钮的点击事件
        ImageButton deleteButton = convertView.findViewById(R.id.deleteButton); // 确保引用了正确的ID
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyManager.removeTranslation(originalText);
                translations.remove(originalText);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
