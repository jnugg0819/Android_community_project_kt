package com.mamba.kt_community.data.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.mamba.kt_community.Adapter.board.BoardAdapter
import com.mamba.kt_community.Adapter.search.SearchAdapter
import com.mamba.kt_community.HomeActivitty
import com.mamba.kt_community.response.board.BoardLikeGetUserInfoResponse
import com.mamba.kt_community.response.board.BoardLikeUpdateResponse
import com.mamba.kt_community.response.board.BoardResponse
import com.mamba.kt_community.response.search.SearchResponse
import com.mamba.kt_community.retrofit.MyAPI
import com.mamba.kt_community.retrofit.RetrofitClient
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel:ViewModel(){

    private val retrofit = RetrofitClient.instance
    private val myAPI= retrofit!!.create(MyAPI::class.java)

    private val _searchGetLiveData=MutableLiveData<BoardResponse>()

    val searchGetLiveData:LiveData<BoardResponse>
        get()=_searchGetLiveData

    fun getFirstTimeLine(recyclerView:RecyclerView,adapter:BoardAdapter){
        myAPI.getTimelineFirst().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.Observer<BoardResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(boardResponse: BoardResponse) {
                    val newBoard = boardResponse.response
                    for(i in newBoard.indices){
                        for(j in HomeActivitty.userInfoList.indices){
                            if (Integer.parseInt(newBoard[i].boardIdx!!) == HomeActivitty.userInfoList[j].boardIdx) {
                                newBoard[i].isLikeCheck=true
                            }
                        }
                    }
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
                    for(i in newBoard.indices){
                        for(j in HomeActivitty.userInfoList.indices){
                            if (Integer.parseInt(newBoard[i].boardIdx!!) == HomeActivitty.userInfoList[j].boardIdx) {
                                newBoard[i].isLikeCheck=true
                            }
                        }
                    }
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
                    for(i in newBoard.indices){
                        for(j in HomeActivitty.userInfoList.indices){
                            if (Integer.parseInt(newBoard[i].boardIdx!!) == HomeActivitty.userInfoList[j].boardIdx) {
                                newBoard[i].isLikeCheck=true
                            }
                        }
                    }
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
                    for(i in newBoard.indices){
                        for(j in HomeActivitty.userInfoList.indices){
                            if (Integer.parseInt(newBoard[i].boardIdx!!) == HomeActivitty.userInfoList[j].boardIdx) {
                                newBoard[i].isLikeCheck=true
                            }
                        }
                    }
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
            .subscribe(object : Observer<BoardResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(boardResponse: BoardResponse) {
                    val newBoard = boardResponse.response
                    for(i in newBoard.indices){
                        for(j in HomeActivitty.userInfoList.indices){
                            if (Integer.parseInt(newBoard[i].boardIdx!!) == HomeActivitty.userInfoList[j].boardIdx) {
                                newBoard[i].isLikeCheck=true
                            }
                        }
                    }
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
            .subscribe(object : Observer<BoardResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(boardResponse: BoardResponse) {
                    val newBoard = boardResponse.response
                    for(i in newBoard.indices){
                        for(j in HomeActivitty.userInfoList.indices){
                            if (Integer.parseInt(newBoard[i].boardIdx!!) == HomeActivitty.userInfoList[j].boardIdx) {
                                newBoard[i].isLikeCheck=true
                            }
                        }
                    }
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

    fun updateLike(boardIdx:Int,userId:String,checker:Boolean){
        myAPI.updateLike(boardIdx,userId,checker)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BoardLikeUpdateResponse> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(boardLikeUpdateResponse: BoardLikeUpdateResponse) {
                    if(boardLikeUpdateResponse.isResponse){
                        Log.d("BoardLike","좋아요 업데이트 성공")
                    }

                }

                override fun onError(e: Throwable) {

                }

            })
    }

    fun insertLikeUserInfo(boardIdx:Int,userId:String){
        myAPI.insertLikeUserInfo(boardIdx,userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<BoardLikeUpdateResponse>{
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(boardLikeUpdateResponse: BoardLikeUpdateResponse) {
                    if(boardLikeUpdateResponse.isResponse){
                        Log.d("BoardLike","유저정보 집어넣기 성공")
                    }
                }

                override fun onError(e: Throwable) {
                }

            })
    }

    fun deleteLikeUserInfo(boardIdx:Int,userId:String){
        myAPI.deleteLikeUserInfo(boardIdx,userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<BoardLikeUpdateResponse>{
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(boardLikeUpdateResponse: BoardLikeUpdateResponse) {
                    if(boardLikeUpdateResponse.isResponse){
                        Log.d("BoardLike","유저정보 삭제 성공")
                    }
                }

                override fun onError(e: Throwable) {

                }

            })
    }

    fun getLikeUserInfo(userId:String){
        myAPI.selectLikeUserInfo(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<BoardLikeGetUserInfoResponse>{
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(boardLikeGetUserInfoResponse: BoardLikeGetUserInfoResponse) {
                    HomeActivitty.userInfoList=boardLikeGetUserInfoResponse.response
                }

                override fun onError(e: Throwable) {
                }

            })

    }
    
    fun selectSearchAll(
        searchTxt: String,
        recyclerView: RecyclerView,
        searchAdapter: SearchAdapter
    ){
        myAPI.selectSearchAll(searchTxt)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<SearchResponse>{


                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(searchResponse: SearchResponse) {
                    searchAdapter.setItems(searchResponse.response)
                    searchAdapter.notifyDataSetChanged()
                }

                override fun onComplete() {
                    recyclerView.adapter=searchAdapter

                }

                override fun onError(e: Throwable) {
                    Log.d("error","${e.printStackTrace()}")

                }

            })
    }





}