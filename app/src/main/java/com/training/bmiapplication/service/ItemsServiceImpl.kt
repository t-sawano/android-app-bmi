package com.training.bmiapplication.service

import android.content.SharedPreferences
import android.util.Log
import com.training.bmiapplication.adapter.RecyclerState
import com.training.bmiapplication.adapter.RecyclerType
import com.training.bmiapplication.dao.ItemsDao
import com.training.bmiapplication.dao.ItemsDaoImpl
import com.training.bmiapplication.entity.ItemsOfBMI
import java.text.SimpleDateFormat
import java.util.*

/**
 * ItemsServiceの実装クラス
 */
class ItemsServiceImpl(pref: SharedPreferences) : ItemsService{

    // ItemsDaoをDIする
    private var itemsDao: ItemsDao = ItemsDaoImpl(pref)

    override fun save(items: ItemsOfBMI): Boolean {
        if(itemsDao.save(items)) {
            itemsDao.flush()
            return true
        }
        return false
    }

    override fun update(id: String?, item: ItemsOfBMI): Boolean {
        // IDがNULLでないことは確定
        id?.let {
            itemsDao.update(id ,item)
            itemsDao.flush()
            return true
        }
        return false
    }

    override fun findNow(): ItemsOfBMI? {
        Log.d("ItemsServiceImpl#findnow" ,"保存したデータを参照します。${this.nowString()}")
        Log.d("ItemsServiceImpl#findnow" ,"取得対象データ -> ${this.itemsDao.findById(this.nowString())}")
        itemsDao.flush()
        return itemsDao.findById(this.nowString())
    }

    override fun delete(id: String): Boolean {
        if (itemsDao.delete(id)) {
            itemsDao.flush()
            return true
        }
        return false
    }

    override fun findAll(): ArrayList<RecyclerState> {
        val items = itemsDao.findAll()

        // sectionは月初のrowの時に表示するので、現在の月を一時保持しておく
        var sectionState: Int? = 0
        val states = arrayListOf<RecyclerState>()

        for (item in items) {

            /** SECTION 条件に一致したら追加する */
            if (sectionState != item.splitMonth()) {
                val section = RecyclerState(RecyclerType.SECTION ,item)
                states.add(section)
            }

            /** BODY 無条件に追加する */
            val body = RecyclerState(RecyclerType.BODY ,item)
            states.add(body)

            // ここじゃくてもいいはず
            sectionState = item.splitMonth()

            /** DETAIL メモに情報が登録されているなら追加する */
            if (!item.memo.isNullOrEmpty()) {
                Log.d("ItemsServiceImpl#findAll" ,"${item.memo}")
                val detail = RecyclerState(RecyclerType.DETAIL ,item)
                states.add(detail)
            }
        }

        // セクションとバディ、メモのrowをそれぞれ格納して返却
        return states
    }

    override fun flushFinal() {
        itemsDao.flush()
    }

    /** ID生成処理 ... */
    private fun nowString(): String {
        val sdf = SimpleDateFormat("yyyyMMdd")
        return sdf.format(Date())
    }
}