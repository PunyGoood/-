package com.example.test1

data class TranslateResult(
    val from: String,
    val to: String,
    val trans_result: List<TransResult>
)