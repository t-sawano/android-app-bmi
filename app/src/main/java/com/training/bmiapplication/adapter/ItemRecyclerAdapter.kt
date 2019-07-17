package com.training.bmiapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.bmiapplication.R

/**
 * RecyclerViewのAdapter
 * RecyclerViewの各種設定をする。
 */
class ItemRecyclerAdapter(private val itemList: ArrayList<RecyclerState>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("ItemRecyclerAdapter#onCreateViewHolder" ,"onCreateViewHolder")

        when (RecyclerType.fromInt((viewType))) {
            /** SECTION */
            RecyclerType.SECTION -> {
                val rowView = LayoutInflater.from(parent.context).inflate(R.layout.section_for_lists ,parent ,false)

                return ItemRecyclerViewSectionHolder(rowView)
            }

            /** BODY */
            RecyclerType.BODY -> {
                val rowView = LayoutInflater.from(parent.context).inflate(R.layout.items_list ,parent ,false)

                return ItemRecyclerViewHolder(rowView)
            }

            /** DETAIL */
            RecyclerType.DETAIL -> {
                val rowView = LayoutInflater.from(parent.context).inflate(R.layout.memo_detail_for_lists ,parent ,false)
                return ItemRecyclerViewDetailHolder(rowView)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("ItemRecyclerAdapter#onBindViewHolder" ,"onBindViewHolder")

        when (holder) {
            // BODYを作成
            is ItemRecyclerViewHolder -> {
                val item = itemList[position].item

                // Viewにパラメータをセットする
                holder.dateView.text    = item.splitDate()?.toString()
                holder.heightView.text  = item.height
                holder.weightView.text  = item.weight
                holder.bmiView.text     = item.calcBMI()

                Log.d("ItemRecyclerAdapter#onBindViewHolder" ,"BODYをセットしました。")
            }

            // SECTIONを作成
            is ItemRecyclerViewSectionHolder -> {
                val item = itemList[position].item

                holder.monthView.text = item.splitMonth().toString()

                Log.d("ItemRecyclerAdapter#onBindViewHolder" ,"SECTIONをセットしました。")
            }

            // DETAILを作成
            is ItemRecyclerViewDetailHolder -> {
                val item = itemList[position].item

                holder.memoView.text = item.memo

                Log.d("ItemRecyclerAdapter#onBindViewHolder" ,"DETAILをセットしました。")
            }
        }


        // 押下時処理を書く場合はここ
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position].type.value
    }

    override fun getItemCount(): Int {
        Log.d("ItemRecyclerAdapter#getItemCount" ,"getItemCount")
        return itemList.size
    }
}