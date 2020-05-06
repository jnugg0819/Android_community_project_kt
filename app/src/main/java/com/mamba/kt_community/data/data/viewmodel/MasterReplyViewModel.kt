package com.mamba.kt_community.data.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.google.android.youtube.player.internal.i
import com.mamba.kt_community.MasterReplyActivity
import com.mamba.kt_community.base.BaseViewModel
import com.mamba.kt_community.data.data.DataModel.DataModel
import com.mamba.kt_community.data.data.reply.Reply
import com.mamba.kt_community.response.reply.ReplyGetResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observer

class MasterReplyViewModel(private val model: DataModel) : BaseViewModel() {

    //외부접근 방지용
    private val _masterReplyGetLiveData = MutableLiveData<ReplyGetResponse>()

    //외부접근 가능용
    val masterReplyGetLiveData: LiveData<ReplyGetResponse>
        get() = _masterReplyGetLiveData

    //댓글정보 가져오기
    fun getMasterReply(boardIdx: Int) {
        addDisposable(
            model.getMasterReply(boardIdx)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        if(response.size>0){
                            Log.d("getReply","받기성공")
                            _masterReplyGetLiveData.postValue(this)
                        }
                    }

                },
                    {
                        Log.d("getReply","받기실패")
                    }
                ))


    }

    //댓글정보 보내기
    fun postMasterReply(boardIdx: Int, creatorId: String, contents: String) {
        addDisposable(model.postMasterReply(boardIdx, creatorId, contents)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    getMasterReply(boardIdx)
                }
            }, {
                Log.d("postReply", "전송실패")
            }

            ))
    }

    //따봉 유저정보 저장
    fun insertMasterReplyThumbsUpUI(boardIdx: Int,masterIdx: Int,pressedId: String){
        addDisposable(model.insertMasterReplyThumbsUpUI(boardIdx,masterIdx,pressedId).subscribeOn(Schedulers.io())
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
    fun updateMasterReplyThumbsUp(boardIdx:Int,masterIdx:Int,pressedId:String,isPressed:Boolean){
        addDisposable(model.updateMasterReplyThumbsUp(boardIdx,masterIdx,pressedId,isPressed).subscribeOn(Schedulers.io())
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

    fun deleteMasterReplyThumbsUpUI(boardIdx: Int,masterIdx: Int,pressedId: String){
        addDisposable(model.deleteMasterReplyThumbsUpUI(boardIdx,masterIdx,pressedId).subscribeOn(Schedulers.io())
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

    fun selectMasterReplyThumbsUpUI(boardIdx: Int,userId:String){
        addDisposable(model.selectMasterReplyThumbsUpUI(boardIdx,userId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it.run {
                        MasterReplyActivity.userInfoList=response
                    }
                },
                {
                    Log.d("updateThumbsUp", "따봉업 유저정보 삭제실패")
                }
            ))
    }

}