package com.training.bmiapplication.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.training.bmiapplication.R

/**
 * BODY用のHolder
 */
class ItemRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val dateView: TextView = itemView.findViewById(R.id.dateColumn)
    val heightView: TextView = itemView.findViewById(R.id.heightColumn)
    val weightView: TextView = itemView.findViewById(R.id.weightColumn)
    val bmiView: TextView = itemView.findViewById(R.id.bmiColumn)

    // val detailView: TextView = itemView.findViewById(R.id.detailColumn)
}

/**
 * SECTION用のHolder
 */
class ItemRecyclerViewSectionHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    val monthView: TextView = itemView.findViewById(R.id.monthSection)
}

/**
 * DETAIL用のHolder
 */
class ItemRecyclerViewDetailHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    val memoView: TextView = itemView.findViewById(R.id.memoDetail)
}