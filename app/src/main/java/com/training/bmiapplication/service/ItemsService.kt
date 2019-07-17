package com.training.bmiapplication.service

import com.training.bmiapplication.adapter.RecyclerState
import com.training.bmiapplication.entity.ItemsOfBMI

/**
 * 共有プリファレンスへのCRUD操作サービス層
 */
interface ItemsService {

    /**
     * 入力値の値を保存する
     * @param items
     * @return Boolean 保存が正常に完了したら true
     */
    fun save(items: ItemsOfBMI): Boolean

    /**
     * 入力値の値を更新する
     * @param id 更新する対象のID
     * @param item 更新する対象のオブジェクト
     */
    fun update(id: String? ,item: ItemsOfBMI): Boolean

    /**
     * 今日日付を使用して、保存済み情報を参照する <br/>
     * @return ItemsOfBMI?
     */
    fun findNow(): ItemsOfBMI?

    /**
     * IDで参照して、登録情報を削除する
     * @param id 削除対象のID
     * @return
     */
    fun delete(id: String) :Boolean

    /**
     * 全件取得
     * RecyclerViewの表示用に整形したリストを返却する。
     * @return List<ItemsOfBmi> 現在の登録情報を取得する
     */
    fun findAll(): ArrayList<RecyclerState>

    /**
     * アプリが終了した際に呼び出し、<br/>
     *     共有プリファレンスに現在の状態を保存する
     */
    fun flushFinal()
}