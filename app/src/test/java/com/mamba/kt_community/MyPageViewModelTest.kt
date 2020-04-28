package com.mamba.kt_community

import com.mamba.kt_community.data.data.viewmodel.MyPageViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock

class MyPageViewModelTest{

    @Test
    fun test(){
        val p=mock(MyPageViewModel::class.java)
        assertTrue(p!=null)


        assertEquals(p.getMyPageText("jgg0819@naver.com", this),false)


    }

}