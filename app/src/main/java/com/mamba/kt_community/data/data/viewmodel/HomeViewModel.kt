package com.mamba.kt_community.data.data.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.mamba.kt_community.Adapter.board.BoardAdapter
import com.mamba.kt_community.response.board.BoardResponse
import com.mamba.kt_community.retrofit.MyAPI
import com.mamba.kt_community.retrofit.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel:ViewModel(){

    private val retrofit = RetrofitClient.instance
    private val myAPI= retrofit!!.create(MyAPI::class.java)

    fun getFirstTimeLine(recyclerView:RecyclerView,adapter:BoardAdapter){
        myAPI.getTimelineFirst().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.Observer<BoardResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(boardResponse: BoardResponse) {
                    val newBoard = boardResponse.response
                    adapter.setItems(newBoard)
                    adapter.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    recyclerView.adapter = adapter
                }
            })
    }

    fun getSecondTimeLine(recyclerView:RecyclerView,adapter:BoardAdapter){
        myAPI.getTimelineSecond().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.Observer<BoardResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(boardResponse: BoardResponse) {
                    val newBoard = boardResponse.response
                    adapter.setItems(newBoard)
                    adapter.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    recyclerView.adapter = adapter
                }
            })
    }

    fun getThirdTimeLine(recyclerView:RecyclerView,adapter:BoardAdapter){
        myAPI.getTimelineThird().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.Observer<BoardResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(boardResponse: BoardResponse) {
                    val newBoard = boardResponse.response
                    adapter.setItems(newBoard)
                    adapter.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    recyclerView.adapter = adapter
                }
            })
    }

    fun getFourthTimeLine(recyclerView:RecyclerView,adapter:BoardAdapter){
        myAPI.getTimelineFourth().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.Observer<BoardResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(boardResponse: BoardResponse) {
                    val newBoard = boardResponse.response
                    adapter.setItems(newBoard)
                    adapter.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    recyclerView.adapter = adapter
                }
            })
    }


    fun getFifthTimeLine(recyclerView:RecyclerView,adapter:BoardAdapter){
        myAPI.getTimelineFifth().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.Observer<BoardResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(boardResponse: BoardResponse) {
                    val newBoard = boardResponse.response
                    adapter.setItems(newBoard)
                    adapter.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    recyclerView.adapter = adapter
                }
            })
    }

    fun getSixthTimeLine(recyclerView:RecyclerView,adapter:BoardAdapter){
        myAPI.getTimelineSixth().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.Observer<BoardResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(boardResponse: BoardResponse) {
                    val newBoard = boardResponse.response
                    adapter.setItems(newBoard)
                    adapter.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    recyclerView.adapter = adapter
                }
            })
    }



}