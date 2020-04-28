package com.mamba.kt_community.data.data.DataModel

import com.mamba.kt_community.data.data.reply.Reply
import com.mamba.kt_community.response.reply.ReplyGetResponse
import com.mamba.kt_community.response.reply.ReplyPostResponse
import io.reactivex.Observable
import io.reactivex.Single

interface DataModel {

    fun getMasterReply(boardIdx:Int): Observable<ReplyGetResponse>

    fun postMasterReply(boardIdx:Int,creatorId:String,contents:String):Single<ReplyPostResponse>
}