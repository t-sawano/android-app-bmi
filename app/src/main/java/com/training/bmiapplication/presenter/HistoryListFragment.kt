package com.training.bmiapplication.presenter


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.bmiapplication.R
import com.training.bmiapplication.adapter.ItemRecyclerAdapter
import com.training.bmiapplication.service.ItemsService
import kotlinx.android.synthetic.main.fragment_history_list.*

/**
 * 履歴表示用のフラグメント
 */
class HistoryListFragment : Fragment() {
    // 共有プリファレンスの情報をDIする
    private lateinit var itemsService: ItemsService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("HistoryFragment#onViewCreated" ,"履歴表示開始")

        val recyclerView = historyContainer
        val adapter = ItemRecyclerAdapter(itemsService.findAll())


        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        Log.d("HistoryFragment#onViewCreated" ,"履歴表示終了")
    }


    /** Activityから共有プリファレンスを扱うクラスをDIする */
    fun intoItemsService(service: ItemsService) {
        Log.d("HistoryListFragment#intoItemsService" ,"フィールド：itemsServiceを初期化しました。")
        this.itemsService = service
    }
}
