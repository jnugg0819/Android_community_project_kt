package com.mamba.kt_community.data.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mamba.kt_community.MasterReplyActivity
import com.mamba.kt_community.SlaveReplyActivity
import com.mamba.kt_community.base.BaseViewModel
import com.mamba.kt_community.data.data.DataModel.DataModel
import com.mamba.kt_community.response.reply.ReplyGetResponse
import com.mamba.kt_community.response.reply.ReplyGetSlaveResponse
import com.mamba.kt_community.retrofit.MyAPI
import com.mamba.kt_community.retrofit.RetrofitClient
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SlaveReplyViewModel(private val model: DataModel):BaseViewModel(){

    private val _slaveReplyGetLiveData = MutableLiveData<ReplyGetSlaveResponse>()

    val slaveReplyGetLiveData: LiveData<ReplyGetSlaveResponse>
        get() = _slaveReplyGetLiveData

    fun getSlaveReply(boardIdx:Int,masterIdx:Int){
        addDisposable(model.getSlaveReply(boardIdx,masterIdx)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                {
                    it.run {
                        if(response!!.size>0){
                            _slaveReplyGetLiveData.postValue(this)
                        }
                    }
                },
                {
                    Log.d("getSlaveReply","받기실패")
                }

            )
        )
    }

    fun postSlaveReply(boardIdx: Int, masterIdx: Int,creatorId: String, contents: String) {
        addDisposable(model.postSlaveReply(boardIdx, masterIdx,creatorId, contents)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    getSlaveReply(boardIdx,masterIdx)
                }
            }, {
                Log.d("postSlaveReply", "전송실패")
            }

            ))
    }

    //따봉 유저정보 저장
    fun insertSlaveReplyThumbsUpUI(boardIdx: Int,masterIdx: Int,slaveIdx:Int,pressedId: String){
        addDisposable(model.insertSlaveReplyThumbsUpUI(boardIdx,masterIdx,slaveIdx,pressedId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it.run {
                        if(isResponse){
                            Log.d("insertThumbsUp", "따봉업 유저정보 인서트성공")
                        }
                    }
                },
                {
                    Log.d("insertThumbsUp", "따봉업 유저정보 인서트실패")
                }


            ))
    }

    //따봉 유저정보 업데이트
    fun updateSlaveReplyThumbsUp(boardIdx:Int,masterIdx:Int,slaveIdx: Int,pressedId:String,isPressed:Boolean){
        addDisposable(model.updateSlaveReplyThumbsUp(boardIdx,masterIdx,slaveIdx,pressedId,isPressed).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it.run {
                        if(isResponse){
                            Log.d("updateThumbsUp", "따봉업 유저정보 업뎃성공")
                        }
                    }
                },
                {
                    Log.d("updateThumbsUp", "따봉업 유저정보 업뎃실패")
                }


            ))
    }

    //따봉 유저정보 삭제
    fun deleteSlaveReplyThumbsUpUI(boardIdx: Int,masterIdx: Int,slaveIdx: Int,pressedId: String){
        addDisposable(model.deleteSlaveReplyThumbsUpUI(boardIdx,masterIdx,slaveIdx,pressedId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it.run {
                        if(isResponse){
                            Log.d("updateThumbsUp", "따봉업 유저정보 삭제성공")
                        }
                    }
                },
                {
                    Log.d("updateThumbsUp", "따봉업 유저정보 삭제실패")
                }
            ))
    }

    //따봉 유저정보 가져오기
    fun selectSlaveReplyThumbsUpUI(boardIdx: Int,masterIdx: Int,userId:String){
        addDisposable(model.selectSlaveReplyThumbsUpUI(boardIdx,masterIdx,userId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it.run {
                        SlaveReplyActivity.userInfoList=response
                    }
                },
                {
                    Log.d("updateThumbsUp", "따봉업 유저정보 삭제실패")
                }
            ))
    }

}