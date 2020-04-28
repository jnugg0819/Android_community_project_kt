package com.mamba.kt_community.di

import com.mamba.kt_community.Adapter.reply.MasterReplyAdapter
import com.mamba.kt_community.data.data.DataModel.DataModel
import com.mamba.kt_community.data.data.DataModel.DataModelImpl
import com.mamba.kt_community.data.data.viewmodel.MasterReplyViewModel
import com.mamba.kt_community.retrofit.MyAPI
import com.mamba.kt_community.retrofit.RetrofitClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

var retrofitPart= module {
    single<MyAPI>{
        Retrofit.Builder()
            .baseUrl("http://192.168.35.50:8080/")
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MyAPI::class.java)
    }
}

var modelPart= module {
    factory <DataModel>{
        DataModelImpl(get())
    }
}

var viewModelPart= module {
    viewModel{
        MasterReplyViewModel(get())
    }
}

var myDiModule= listOf(retrofitPart,modelPart,viewModelPart)





private fun createOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    builder.addInterceptor(interceptor)
    return builder.build()
}