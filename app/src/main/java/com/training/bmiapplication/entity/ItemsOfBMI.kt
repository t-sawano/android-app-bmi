package com.training.bmiapplication.entity

import android.util.Log
import com.fasterxml.jackson.annotation.JsonProperty
import java.text.SimpleDateFormat
import java.util.*

/**
 * リストに表示するセルの中身
 */
open class ItemsOfBMI(
    var id: String?,
    var height: String,
    var weight: String,
    var memo: String?) {

    init {
        if(this.id == null) {
            this.id = createID()
        }
    }

    fun splitMonth(): Int? {
        this.id?.let {
            return it.substring(4 ,6).toInt()
        }
        return null
    }

    /**
     * 履歴画面表示用の日付
     * 形式は日のみ（dd）
     * @return String dd
     */
    fun splitDate(): Int? {
        this.id?.let {
            return it.substring(it.length - 2).toInt()
        }
        return null
    }

    /**
     * フィールドの身長と体重を元にBMIを計算し結果を返却する
     * @return BMI
     */
    fun calcBMI(): String {
        val heightToM = this.height.toDouble() / 100
        // 身長の二乗
        val heightDouble: Double = heightToM * heightToM
        // BMIを計算する
        val bmi = (this.weight.toDouble() / heightDouble)

        // BMIの結果を返却する
        return String.format("%.1f" ,bmi)
    }

    /** 検証用 */
    override fun toString(): String {
        return "ID : ${this.id} Height : ${this.height} Weight : ${this.weight} Memo : ${this.memo}"
    }

    /**
     * 今日日付を「yyyyMMdd」の形式で整形する
     */
    private fun createID(): String {
        val sdf = SimpleDateFormat("yyyyMMdd")
        return sdf.format(Date())
    }
}