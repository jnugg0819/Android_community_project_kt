package com.mamba.kt_community.data.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mamba.kt_community.base.BaseViewModel
import com.mamba.kt_community.data.data.DataModel.DataModel
import com.mamba.kt_community.data.data.reply.Reply
import com.mamba.kt_community.response.reply.ReplyGetResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MasterReplyViewModel(private val model: DataModel) : BaseViewModel() {


    private val _masterReplyGetLiveData = MutableLiveData<ReplyGetResponse>()

    val masterReplyGetLiveData: LiveData<ReplyGetResponse>
        get() = _masterReplyGetLiveData

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
                    })
        )

    }


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
}