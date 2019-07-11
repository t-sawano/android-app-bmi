package com.training.bmiapplication.entity

import java.text.SimpleDateFormat
import java.util.*

/**
 * リストに表示するセルの中身
 * @param id 登録対象日付 yyyyMMddのString nullの場合はidを自動生成
 * @param height 身長
 * @param weight 体重
 * @param memo メモ
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

    /**
     * セクション表示用 IDから月の値を取得して数値型で返却する<br>
     * dtoなりを作成して、そっちに作ればよかった...
     * @return idフィールドがnullになることはないので必ず数値型で返却される。
     */
    fun splitMonth(): Int? {
        this.id?.let {
            return it.substring(4 ,6).toInt()
        }
        return null
    }

    /**
     * 履歴画面に表示用の日付
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
        return "id : ${this.id} height : ${this.height} weight : ${this.weight} memo : ${this.memo}"
    }

    /**
     * 今日日付を「yyyyMMdd」の形式で整形する
     */
    private fun createID(): String {
        val sdf = SimpleDateFormat("yyyyMMdd")
        return sdf.format(Date())
    }
}