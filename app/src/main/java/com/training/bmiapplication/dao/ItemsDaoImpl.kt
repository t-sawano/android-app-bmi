package com.training.bmiapplication.dao

import android.content.SharedPreferences
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.training.bmiapplication.entity.ItemsOfBMI

const val KEY_ITEMS_LIST = "KEY_ITEM_LIST"

/** 共有プリファレンスをここで操作する */
class ItemsDaoImpl(sharedPreferences: SharedPreferences) : ItemsDao {

    private var itemsList = mutableSetOf<ItemsOfBMI>()
    private var pref = sharedPreferences

    init {
        // 共有プリファレンスの情報を読み込む
        this.readSharedPreference()
    }

    override fun save(item: ItemsOfBMI): Boolean{
        // Idが一致するなら同一オブジェクトが存在するため保存しない
        itemsList.forEach {
            if(it.id == item.id) {
                Log.d("ItemsDaoImp#save" ,"${it.id} == ${item.id} -> ${it.id.equals(item.id)}")
                return false
            }
        }
        // 同一IDが存在しない場合、新規保存
        itemsList.add(item)
        return true
    }

    override fun findAll(): List<ItemsOfBMI> {
        // 共有プリファレンスの状態を読み込み
        this.readSharedPreference()

        // IDに日付を登録しているので、IDでソートして返却する。
        return this.itemsList.toList().sortedBy { it.id }
    }

    override fun findById(id: String): ItemsOfBMI?{
        // 共有プリファレンスの状態を読み込み
        this.readSharedPreference()

        itemsList.forEach {
            Log.d("" ,"$it")
            if (it.id == id) {
                // IDが一致したレコードを返却する
                return it
            }
        }
        // 対象のIDがなければNULLを返却する
        return null
    }

    override fun update(id: String, item: ItemsOfBMI): Boolean {
        itemsList.forEach {
            if(it.id == id) {
                // 更新対象の要素を削除し、新しく更新したものを追加する
                itemsList.remove(it)
                itemsList.add(item)

                // 更新が完了したら true
                return true
            }
        }
        // 更新対象がなかったら false
        return false
    }

    override fun delete(id: String): Boolean {
        itemsList.forEach {
            if(it.id == id) {
                itemsList.remove(it)

                // 削除が完了したらtrue
                return true
            }
        }
        return false
    }

    override fun flush() {
        val editor = this.pref.edit()

        // 何も保持していない場合は共有プリファレンスから削除する
        if (this.itemsList.isNullOrEmpty()) {
            editor.remove(KEY_ITEMS_LIST)
                  .apply()
            // ここの処理が走ったら以下処理はいらないためreturnする
            return
        }

        // 共有プリファレンスに現在の状態を保存する。
        val saved = editor.putStringSet(KEY_ITEMS_LIST ,convertJsonSet())
              .commit()

        Log.d("ItemsDaoImpl#flush" ,"共有プリファレンスへの保存が完了しました。")
    }

    /**
     * 共有プリファレンスの情報を読み込んでキャッシュする。 <br />
     * ここでは、共有プリファレンスに登録はしない。
     */
    private fun readSharedPreference() {
        val list = pref.getStringSet(KEY_ITEMS_LIST ,null)

        list?.let {
            itemsList = jsonSetToObject(it)
        }
    }

    /**
     * SharedPreferenceから取得したMutableSet<String>をオブジェクトとして使えるようにする
     * @param {MutableSet<String>} SharedPreferenceから取得した値
     * @return {MutableSet<ItemsOfBMI?>} jsonなStringをItemsOfBMIに変換した値が返却される
     */
    private fun jsonSetToObject(items: MutableSet<String>): MutableSet<ItemsOfBMI> {

        val itemsList = mutableSetOf<ItemsOfBMI>()
        items.forEach {
            it.let {
                itemsList.add(parseJSON(it))
            }
        }

        // 全ての値が未登録の場合はNULLが返却される。
        return itemsList
    }

    /**
     * itemsListをSharedPreferenceのputStringSet()で使用できるようにコンバートする
     * @return {MutableSet<String>}
     */
    private fun convertJsonSet(): MutableSet<String> {
        var list = mutableSetOf<String>()
        val mapper = ObjectMapper()

        this.itemsList.forEach {
            val jsonString = mapper.writeValueAsString(it)
            list.add(jsonString)
        }

        // ItemsOfBMIをJSONのString形式に変換する。
        return list
    }

    /**
     * JSON文字列をオブジェクトに変換する
     * @param {json} JSON文字列
     * @return JSONをパースしたオブジェクトが返却される
     */
    private inline fun <reified T : Any> parseJSON(json: String) : T {
        // json形式のオブジェクトをパースする。
        return jacksonObjectMapper().readValue(json ,T::class.java)
    }
}