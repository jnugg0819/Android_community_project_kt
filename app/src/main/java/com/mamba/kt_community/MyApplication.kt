package com.mamba.kt_community

import android.app.Application
import com.mamba.kt_community.di.myDiModule
import org.koin.android.ext.android.startKoin

class MyApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext,myDiModule)
    }
}