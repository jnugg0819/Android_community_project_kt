package com.mamba.kt_community.data.data.DataModel

import com.mamba.kt_community.data.data.reply.Reply
import com.mamba.kt_community.response.reply.ReplyGetResponse
import com.mamba.kt_community.response.reply.ReplyPostResponse
import com.mamba.kt_community.retrofit.MyAPI
import io.reactivex.Observable
import io.reactivex.Single

class DataModelImpl(private val service:MyAPI) :DataModel{


    override fun postMasterReply(
        boardIdx: Int,
        creatorId: String,
        contents: String
    ): Single<ReplyPostResponse> {
        return service.postReply(boardIdx=boardIdx,creatorId = creatorId,contents = contents)
    }

    override fun getMasterReply(boardIdx: Int): Observable<ReplyGetResponse> {
        return service.getReply(boardIdx=boardIdx)
    }
}