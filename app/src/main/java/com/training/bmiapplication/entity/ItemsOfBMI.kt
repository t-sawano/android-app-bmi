package com.training.bmiapplication.entity

// TODO 好みの問題もありそうですが、dataとロジックは分割したいです data class にしたい。
/**
 * リストに表示するセルの中身
 * @param id 登録対象日付 yyyyMMddのString nullの場合はidを自動生成
 * @param height 身長
 * @param weight 体重
 * @param memo メモ
 */
class ItemsOfBMI(
    var id: String,
    var height: String,
    var weight: String,
    var memo: String?) {

    /**
     * セクション表示用 IDから月の値を取得して数値型で返却する<br>
     * dtoなりを作成して、そっちに作ればよかった...
     * @return idフィールドがnullになることはないので必ず数値型で返却される。
     */
    fun splitMonth(): Int {
        return this.id.substring(4 ,6).toInt()
    }

    /**
     * 履歴画面に表示用の日付
     * 形式は日のみ（dd）
     * @return String dd
     */
    fun splitDate(): Int? {
        return this.id.substring(id.length - 2).toInt()

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

}