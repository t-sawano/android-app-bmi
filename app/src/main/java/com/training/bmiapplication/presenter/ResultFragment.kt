package com.training.bmiapplication.presenter


import android.app.AlertDialog
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.training.bmiapplication.R
import com.training.bmiapplication.entity.ItemsOfBMI
import com.training.bmiapplication.service.ItemsService
import com.training.bmiapplication.service.ItemsServiceImpl
import kotlinx.android.synthetic.main.fragment_input.*
import kotlinx.android.synthetic.main.fragment_result.*

/**
 * BMIの結果を表示するFragment
 * メモを入力する欄と保存ボタン、削除ボタンの機能を持つ
 */
class ResultFragment : Fragment(){

    // ここに入力情報をセットする
    // 計算するボタンよりも先に保存するボタンが押下された時用にnullで初期化
    private var item : ItemsOfBMI? = null

    // 共有プリファレンスを操作するクラスを定義する
    private lateinit var itemsService: ItemsService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        // とりあえず動かなくなったので冗長表現に戻しておく。
        this.itemsService = ItemsServiceImpl(PreferenceManager.getDefaultSharedPreferences(this.activity))

        /** 保存ボタン押下時処理 */
        val saveButton = view?.findViewById<View>(R.id.save)
        saveButton?.setOnClickListener {
            Log.d("ResultFragment#onCreateView" ,"「保存」ボタンが押下されました")

            // 先に計算ボタンを押すことが前提なので共有プリファレンスに登録情報があるはず！
            item = itemsService.findNow()
//            Log.d("ResultFragment#onCreateView" ,"保存対象 => $item")

            /** 計算するボタン押下前よりも先に保存ボタンが押下された時、保存しないようにする。 */
            item?.let {
                // メモ欄の入力情報をセットする
                it.memo = inputDetail.text?.toString()

                // ちょっと冗長... 余裕があったら修正する(中身)
                itemsService.update(it.id ,it)

                // メッセージダイアログを表示する 詳細設計には書いてなかったけど使いにくいから勝手に追加
                AlertDialog.Builder(activity)
                    .setMessage("保存したよ！")
                    .setPositiveButton("OK" ,null)
                    .show()

                Log.d("ResultFragment#onCreateView" ,"更新処理が正常に完了しました。")
            }
        }

        /** 削除ボタン押下時処理 */
        val deleteButton = view?.findViewById<View>(R.id.delete)
        deleteButton?.setOnClickListener {
            Log.d("ResultFragment#onCreateView" ,"「削除」ボタンが押下されました。")

            // 削除ボタンが押下されたということは、削除対象があるはず！
            item = itemsService.findNow()

            // Entityと一応Formだから分けた方がよかった...（nullableの扱いが面倒...）
            item?.id?.let {
                itemsService.delete(it)

                // メッセージダイアログを表示する 仕様にはないよ
                AlertDialog.Builder(activity)
                    .setMessage("消しちゃったよ！")
                    .setPositiveButton("OK" ,null)
                    .show()

                /** 削除したのに画面に入力情報が残っていると勘違いしそう... */
                // 入力欄のFragmentを取得する
                val parent = parentFragment

                val height = parent?.heightInput
                val weight = parent?.weightInput
                val result = parent?.bmiResult

                height?.setText("")
                weight?.setText("")

                result?.text = "00.0"
                inputDetail?.setText("")

                Log.d("ResultFragment#onCreateView" ,"削除処理が正常に完了しました。")
            }

        }
        // Inflate the layout for this fragment
        return view
    }
}
