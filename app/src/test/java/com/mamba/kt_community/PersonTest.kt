package com.mamba.kt_community

import com.mamba.kt_community.test.Person
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class PersonTest{

    @Test
    fun test(){

        val p: Person =mock(Person::class.java)
        assertTrue(p!=null)

        `when`(p.name).thenReturn("Seonoh")
        `when`(p.age).thenReturn(28)

        assertEquals(p.name,"seonoh")
        assertEquals(p.age,28)
    }
}