package com.training.bmiapplication.presenter


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.training.bmiapplication.R
import com.training.bmiapplication.entity.ItemsOfBMI
import com.training.bmiapplication.service.ItemsService
import com.training.bmiapplication.util.createID
import kotlinx.android.synthetic.main.fragment_input.*
import kotlinx.android.synthetic.main.fragment_result.*

class InputFragment : Fragment() {

    companion object {
        /** 正規表現 数字3桁 + 小数点一桁 */
        const val INPUT_REGEX = """\d{1,3}(\.\d)?"""
        const val HEIGHT_TYPE = "身長"
        const val WEIGHT_TYPE = "体重"

        /**
         * 入力値の空文字チェック
         * @param text 入力欄のID
         * @param type 入力欄種類
         * @return boolean エラーの場合 false : 空文字では無い場合 true
         */
        fun textIsEmpty(text: EditText ,type: String): Boolean {
            // 空文字判定
            if(text.text.toString().isEmpty()) {
                text.error = "${type}の値を入力してください。"
                return false
            }
            // 空白でなければtrueの返却
            return true
        }

        /**
         * 入力値のバリデーションチェック
         * @param text 入力欄のID
         * @param type 入力欄種類
         * @return boolean エラーの場合 false : 正規表現にマッチした場合 true
         */
        fun textCheckRegex(text: EditText ,type: String ,regexString: String): Boolean {
            val regex = regexString.toRegex()
            // 正規表現チェック
            if(!regex.matches(text.text.toString())) {
                text.error = """
                    |${type}の入力形式が違います。
                    |入力形式は、数値3桁と小数点第一位までです。""".trimMargin()
                return false
            }
            // 正規表現に合致すればtrueを返却
            return true
        }
    }

    // ここに入力情報をセットする
    private lateinit var item : ItemsOfBMI

    // 共有プリファレンスの情報をDIする
    private lateinit var itemsService: ItemsService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_input, container, false)


        itemsService.findNow()?.let {
            Log.d("InputFragment#onCreateView" ,"初期表示開始")
            // 今日日付で登録した情報がある場合に画面への初期表示を行う
            item = it

            val height = view.findViewById<TextView>(R.id.heightInput)
            val weight = view.findViewById<TextView>(R.id.weightInput)
            val result = view.findViewById<TextView>(R.id.bmiResult)
            val memo = view.findViewById<TextView>(R.id.inputDetail)

            // InputFragment内のView
            height.text = item.height
            weight.text = item.weight

            // ResultFragmentのView
            result.text = item.calcBMI()
            memo.text = item.memo

            Log.d("InputFragment#onCreateView" ,"初期表示終了")
        }

        /** 「BMIを計算する」ボタン押下時処理 */
        val button = view.findViewById<View>(R.id.calcButton)
        button?.setOnClickListener {
            // 検証用メッセージ
            Log.d("InputFragment#onCreateView", "「BMIを計算する」ボタンが押下されました")

            if (textIsEmpty(heightInput ,HEIGHT_TYPE)
                && textIsEmpty(weightInput , WEIGHT_TYPE)
                && textCheckRegex(heightInput , HEIGHT_TYPE , INPUT_REGEX)
                && textCheckRegex(weightInput , WEIGHT_TYPE , INPUT_REGEX)) {

                // 身長と体重の入力情報をセットする
                item = ItemsOfBMI(
                    createID(),
                    heightInput.text.toString(),
                    weightInput.text.toString(),
                    inputDetail?.text?.toString() // 「計算する」ボタンが押された時にメモ欄に入力があれば登録する。
                )

                // BMIを表示する
                bmiResult.text = item.calcBMI()

                // 計算したBMIをオブジェクトに保存
                if (!itemsService.save(item)) {
                    itemsService.update(item.id, item)
                }
            }
        }
        return view
    }

    fun intoItemsService(service: ItemsService) {
        Log.d("InputFragment#intoItemsService" ,"フィールド：itemsServiceを初期化しました。")
        this.itemsService = service
    }

    fun getItemsService(): ItemsService {
        return this.itemsService
    }
}


