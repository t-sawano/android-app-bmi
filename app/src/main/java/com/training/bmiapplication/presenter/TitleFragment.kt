package com.training.bmiapplication.presenter


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.training.bmiapplication.R
import kotlinx.android.synthetic.main.fragment_title.*

/**
 * タイトル表示用フラグメント
 */
class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_title, container, false)
    }

    fun setTitle(title: String) {
        titleText.text = title
    }

}
