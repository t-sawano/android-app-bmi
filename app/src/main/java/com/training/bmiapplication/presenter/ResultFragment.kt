package com.training.bmiapplication.presenter


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.training.bmiapplication.R
import com.training.bmiapplication.entity.ItemsOfBMI
import com.training.bmiapplication.service.ItemsService
import com.training.bmiapplication.service.ItemsServiceImpl
import kotlinx.android.synthetic.main.fragment_result.*

private const val KEY_BMI = "KEY_BMI"

class ResultFragment : Fragment(){

    // ここに入力情報をセットする
    // 計算するボタンよりも先に保存するボタンが押下された時用にnullで初期化
    private var item : ItemsOfBMI? = null

    // 共有プリファレンスの情報をDIする
    private lateinit var pref: SharedPreferences
    private lateinit var itemsService: ItemsService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        // Activityから共有プリファレンスの情報を取得する
        pref = PreferenceManager.getDefaultSharedPreferences(activity)
        // ItemsServiceにDIする
        itemsService = ItemsServiceImpl(pref)

        /** 保存ボタン押下時処理 */
        val saveButton = view.findViewById<View>(R.id.save)
        saveButton?.setOnClickListener {
            Log.d("ResultFragment#onCreateView" ,"「保存」ボタンが押下されました")

            item = itemsService.findNow()
            Log.d("ResultFragment#onCreateView" ,"保存対象 => $item")

            item?.let {
                // メモ欄の入力情報をセットする
                it.memo = inputDetail.text.toString()

                // ちょっと冗長... 余裕があったら修正する
                itemsService.update(it.id ,it)

                Log.d("ResultFragment#onCreateView" ,"更新処理が正常に完了しました。")
            }
        }

        /** 削除ボタン押下時処理 */
        val deleteButton = view.findViewById<View>(R.id.delete)
        deleteButton?.setOnClickListener {
            Log.d("ResultFragment#onCreateView" ,"「削除」ボタンが押下されました。")

            item = itemsService.findNow()

            // Entityと一応Formだから分けた方がよかった...
            item?.id?.let {
                itemsService.delete(it)

                Log.d("ResultFragment#onCreateView" ,"削除処理が正常に完了しました。")
            }

        }

        // Inflate the layout for this fragment
        return view
    }

    private var bmiResult: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bmiResult = it.getDouble(KEY_BMI)
        }
    }
}
