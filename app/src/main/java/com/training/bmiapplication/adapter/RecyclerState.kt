package com.training.bmiapplication.adapter

import com.training.bmiapplication.entity.ItemsOfBMI

/** RecyclerViewの表示タイプを保存するクラス */
class RecyclerState() {
    constructor(type: RecyclerType ,item: ItemsOfBMI) : this() {
        this.type = type
        this.item = item

        // Log.d("RecyclerState" ,"$type $item")
    }

    lateinit var type: RecyclerType
    lateinit var item: ItemsOfBMI
}

enum class RecyclerType(val value: Int) {
    BODY(0),
    SECTION(1) ,
    DETAIL(2) ;

    companion object {
        fun fromInt(value: Int): RecyclerType {
            return values()?.firstOrNull {
                it.value == value
            } ?: BODY
        }
    }
}