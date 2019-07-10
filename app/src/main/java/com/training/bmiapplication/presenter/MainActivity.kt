package com.training.bmiapplication.presenter

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import com.training.bmiapplication.R
import com.training.bmiapplication.service.ItemsService
import com.training.bmiapplication.service.ItemsServiceImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_footer.*

private const val INPUT_TITLE_TEXT = "入力"
private const val HISTORY_TITLE_TEXT = "履歴"

class MainActivity : AppCompatActivity() {

    // 共有プリファレンスの情報をDIする
    private lateinit var pref: SharedPreferences
    private lateinit var itemsService: ItemsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleFragment = titleFragment as? TitleFragment
        titleFragment?.setTitle(INPUT_TITLE_TEXT)

        // 初期表示を入力画面とする
        val inputFragment = InputFragment()
        val fragmentManager = this.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.container,inputFragment)
            .addToBackStack(null)
            .commit()

        // 共有プリファレンスをMainActivityで使用する
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        // ItemsServiceにDIする
        itemsService = ItemsServiceImpl(pref)

        /** フッターメニュー 入力画面表示処理 */
        inputScreen.setOnClickListener {
            Log.d("MainActivity#onCreate" ,"入力画面を表示します")

            // タイトルを入力画面用に変更する
            titleFragment?.setTitle(INPUT_TITLE_TEXT)

            // 入力画面フラグメントに差し替え
            val inputFragment = InputFragment()
            val fragmentManager = this.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.container,inputFragment)
                .addToBackStack(null)
                .commit()
            Log.d("MainActivity#onCreate" ,"入力画面を表示が終わりました。")
        }

        /** フッターメニュー 履歴表示処理 */
        historyScreen.setOnClickListener {
            Log.d("MainActivity#onCreate" ,"履歴画面を表示します")

            // タイトルを履歴画面用に変更する
            titleFragment?.setTitle(HISTORY_TITLE_TEXT)

            // 履歴画面フラグメントに差し替え
            val fragment = HistoryListFragment()
            val fragmentManager = this.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.container,fragment)
                .addToBackStack(null)
                .commit()

            Log.d("MainActivity#onCreate" ,"履歴画面の表示が終わりました。")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity#onDestroy" ,"アプリ終了時処理が開始しました。")

        // アプリ終了時に共有プリファレンスの状態を保存する
        itemsService.flushFinal()

        Log.d("MainActivity#onDestroy" ,"アプリ終了時処理が終了しました。")
    }
}