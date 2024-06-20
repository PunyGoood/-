package com.example.test1;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TranslationHistoryManager {
    private static final String HISTORY_KEY_PREFIX = "translation_";

    private SharedPreferences sharedPreferences;

    public TranslationHistoryManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // 保存翻译记录
    public void saveTranslation(String originalText, String translation) {
        sharedPreferences.edit().putString(HISTORY_KEY_PREFIX + originalText, translation).apply();
    }

    // 获取所有翻译记录的键
    public List<String> getHistoryKeys() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        List<String> historyKeys = new ArrayList<>();
        for (String key : allEntries.keySet()) {
            if (key.startsWith(HISTORY_KEY_PREFIX)) {
                historyKeys.add(key.substring(HISTORY_KEY_PREFIX.length()));
            }
        }
        return historyKeys;
    }

    // 获取具体翻译记录
    public String getTranslation(String originalText) {
        return sharedPreferences.getString(HISTORY_KEY_PREFIX + originalText, null);
    }

    // 删除单个翻译记录
    // 删除单个翻译记录
    public void removeTranslation(String originalText) {
        sharedPreferences.edit().remove(HISTORY_KEY_PREFIX + originalText).apply();
    }
    // 清除所有翻译记录
    public void clearHistory() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (String key : allEntries.keySet()) {
            if (key.startsWith(HISTORY_KEY_PREFIX)) {
                editor.remove(key);
            }
        }
        editor.apply();
    }
}