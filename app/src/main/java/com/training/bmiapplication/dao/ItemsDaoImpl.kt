package com.training.bmiapplication.dao

import android.content.SharedPreferences
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.training.bmiapplication.entity.ItemsOfBMI

const val KEY_ITEMS_LIST = "KEY_ITEM_LIST"

class ItemsDaoImpl(sharedPreferences: SharedPreferences) : ItemsDao {

    private var itemsList = mutableSetOf<ItemsOfBMI>()
    private var pref = sharedPreferences

    init {
        val json = pref.getStringSet(KEY_ITEMS_LIST , null)

        json?.let {
            itemsList = jsonSetToObject(it)
        }
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

        itemsList.forEach {
            Log.d("ItemsDaoImp#findAll ソート前 =>" ,"$it")
        }

        this.itemsList.toList().sortedBy { it.id }.forEach {
            Log.d("ItemsDaoImp#findAll ソート後 =>" ,"$it")
        }

        return this.itemsList.toList().sortedBy { it.id }
    }

    override fun findById(id: String): ItemsOfBMI?{
        // 共有プリファレンスの状態を読み込み
        this.readSharedPreference()

        itemsList.forEach {
            Log.d("" ,"$it")
            if (it.id == id) {
                Log.d("ItemsDaoImpl#findById" ,"$id == ${it.id} => ${it.id.equals(id)}")
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

                Log.d("target delete" ,"削除対象 => $it")
                
                Log.d("target update" ,"更新対象 => $item")

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

        Log.d("ItemsDaoImpl#flush" ,"""
            |itemsList size => ${this.itemsList.size} 
            |itemsList isEmpty => ${this.itemsList.isEmpty()}""".trimMargin())

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

        Log.d("ItemsDaoImpl#flush" ,"共有プリファレンスへの保存が完了しました。 結果 -> $saved")
    }

    /**
     *
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

        this.itemsList.forEach {
            Log.d("ItemDaoImpl#convertJsonSet() : オブジェクトの表示" ," => $it")
        }
        list.forEach {
            Log.d("ItemDaoImpl#convertJsonSet() : 全件出力" ," => $it")
        }

        return list
    }

    /**
     * JSON文字列をオブジェクトに変換する
     * @param {json} JSON文字列
     * @return JSONをパースしたオブジェクトが返却される
     */
    private inline fun <reified T : Any> parseJSON(json: String) : T {
        return jacksonObjectMapper().readValue(json ,T::class.java)
    }
}