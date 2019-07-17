package com.training.bmiapplication.adapter

import com.training.bmiapplication.entity.ItemsOfBMI

/** RecyclerViewの表示タイプを保存するクラス */
data class RecyclerState(val type: RecyclerType , val item: ItemsOfBMI)

/** 履歴表示の場合わけをするenumクラス */
enum class RecyclerType(val value: Int) {
    BODY(0),
    SECTION(1) ,
    DETAIL(2) ;

    companion object {
        fun fromInt(value: Int): RecyclerType {
            return values().firstOrNull {
                it.value == value
            } ?: BODY
        }
    }
}