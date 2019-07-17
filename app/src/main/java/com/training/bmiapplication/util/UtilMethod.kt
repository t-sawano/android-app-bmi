package com.training.bmiapplication.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.JAPAN

/**
 * 今日日付を「yyyyMMdd」の形式で整形する
 */
fun createID(): String {
    val sdf = SimpleDateFormat("yyyyMMdd" , JAPAN)
    return sdf.format(Date())
}
