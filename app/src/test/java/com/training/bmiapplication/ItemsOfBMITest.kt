package com.training.bmiapplication

import android.util.Log
import com.training.bmiapplication.entity.ItemsOfBMI
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class ItemsOfBMITest {
    /**
     * BMI計算の単体テストクラス
     */
    @Test
    fun addition_isCorrect() {
        val id = "000000000"

        val height = 180.0.toString()
        val weight = 90.0.toString()
        val memo = "this message is sample message"

        // ネットで引っ張ってきたBMIの値
        val target = 27.8.toString()

        val item = ItemsOfBMI(id ,height ,weight ,memo)

        val bmi = item.calcBMI()

        println("item -> ${item.id} ${item.height} ${item.weight} ${item.memo}")
        println("bmi -> $bmi")

        // ここが違っていたら作り直し
        Assert.assertEquals(bmi ,target)

        val itemIdOfNull = ItemsOfBMI(null ,height ,weight ,null)

        println("id -> ${itemIdOfNull.id}")
        println("month -> ${itemIdOfNull.splitMonth()?.toString()}月")

        // この辺りは他の処理...
        Assert.assertNotNull(itemIdOfNull.id)
        Assert.assertNull(itemIdOfNull.memo)

    }
}