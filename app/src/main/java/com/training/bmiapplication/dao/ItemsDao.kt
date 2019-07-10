package com.training.bmiapplication.dao

import com.training.bmiapplication.entity.ItemsOfBMI

interface ItemsDao {
    /**
     * 新規保存
     */
    fun save(item: ItemsOfBMI): Boolean
    /**
     * 全件取得
     */
    fun findAll(): List<ItemsOfBMI>
    /**
     * 一件取得
     */
    fun findById(id: String): ItemsOfBMI?
    /**
     * 更新
     */
    fun update(id: String ,item: ItemsOfBMI): Boolean
    /**
     * 削除
     */
    fun delete(id: String): Boolean
    /**
     * SharedPreferenceに現在の状態を保存する
     */
    fun flush()
}