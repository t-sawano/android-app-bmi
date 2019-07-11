package com.training.bmiapplication.dao

import com.training.bmiapplication.entity.ItemsOfBMI

interface ItemsDao {
    /**
     * 新規保存 <br>
     * 新規保存のみ、既存IDが存在する場合は保存せずにfalseを返却する
     * @param item 保存対象データ
     * @return 新規保存に成功したらtrue 同一IDが存在する場合はfalse
     */
    fun save(item: ItemsOfBMI): Boolean

    /**
     * 全件取得 <br>
     * @return 保存したレコードの一覧を返却する
     */
    fun findAll(): List<ItemsOfBMI>

    /**
     * 一件取得 <br>
     * IDを参照し、対象のレコードを１件取得する
     * @param id 取得対象レコードのID
     * @return 対象レコード または null
     */
    fun findById(id: String): ItemsOfBMI?

    /**
     * 更新 <br>
     * IDを参照し、同一IDがある場合は更新する<br>
     * 更新対象がない場合は何もせずfalseを返却する
     * @param id 更新対象のID
     * @param item 更新対象のレコード
     * @return 更新に成功したらtrue
     */
    fun update(id: String ,item: ItemsOfBMI): Boolean

    /**
     * 削除 <br>
     * IDを参照して、対象データを削除する
     * @param id 削除対象データのID
     * @return 削除に成功したらtrue
     */
    fun delete(id: String): Boolean

    /**
     * SharedPreferenceに現在の状態を保存する
     */
    fun flush()
}