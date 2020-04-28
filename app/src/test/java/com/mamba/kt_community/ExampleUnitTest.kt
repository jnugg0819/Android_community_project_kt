package com.mamba.kt_community

import androidx.lifecycle.ViewModelProvider
import com.mamba.kt_community.data.data.viewmodel.MyPageViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */



class ExampleUnitTest {

    lateinit var mypageViewModel:MyPageViewModel

    @Before
    fun setUp(){
        mypageViewModel=MyPageViewModel()
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(5 ,2 + 2)
    }
}
