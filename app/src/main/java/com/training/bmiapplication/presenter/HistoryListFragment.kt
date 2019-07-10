package com.training.bmiapplication.presenter


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.bmiapplication.R
import com.training.bmiapplication.adapter.ItemRecyclerAdapter
import com.training.bmiapplication.adapter.RecyclerState
import com.training.bmiapplication.adapter.RecyclerType
import com.training.bmiapplication.entity.ItemsOfBMI
import com.training.bmiapplication.service.ItemsService
import com.training.bmiapplication.service.ItemsServiceImpl
import kotlinx.android.synthetic.main.fragment_history_list.*
import java.text.SimpleDateFormat
import java.util.*

class HistoryListFragment : Fragment() {
    // 共有プリファレンスの情報をDIする
    private lateinit var pref: SharedPreferences
    private lateinit var itemsService: ItemsService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // なんで赤い波線はいるんや...
        pref = PreferenceManager.getDefaultSharedPreferences(activity)
        // ItemsServiceにDIする
        itemsService = ItemsServiceImpl(pref)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("HistoryFragment#onViewCreated" ,"履歴表示開始")

        val recyclerView = historyContainer
        //val adapter = ItemRecyclerAdapter(createDataList())// 検証用
        val adapter = ItemRecyclerAdapter(itemsService.findAll())


        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        recyclerView.adapter = adapter

        Log.d("HistoryFragment#onViewCreated" ,"履歴表示終了")
    }

    /** 検証用 */
    private fun createDataList(): ArrayList<RecyclerState> {

        // val dataList = mutableListOf<ItemsOfBMI>()
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyyMMdd")

        var section: Int? = 0
        val states = arrayListOf<RecyclerState>()


        for (i in 1..40) {
            calendar.add(Calendar.DATE ,3)

            val data = ItemsOfBMI(id = sdf.format(calendar.time.time)
                , height = (i * 10).toString()
                , weight = i.toString()
                , memo = "${i}番目のテストメッセージだよ"
            )

            /** 生成されたデータのうち、月初ならセクションを追加する */
            if (section != data.splitMonth()) {
                val sectionState = RecyclerState(RecyclerType.SECTION ,data)
                //Log.d("HistoryListFragment#createDataList" ,"セクションを追加します。")
                states.add(sectionState)
            }

            /** 生成された情報のうち、メモ以外を表示する。 */
            val sectionState = RecyclerState(RecyclerType.BODY ,data)
            states.add(sectionState)

            section = data.splitMonth()

            /** メモにデータが登録されている場合はメモの内容を表示する。 */
            data.memo?.let {
                val sectionState = RecyclerState(RecyclerType.DETAIL ,data)
                states.add(sectionState)

                Log.d("HistoryListFragment#createDataList" ,"DETAILを追加します。")
            }
        }

        return states
    }


}