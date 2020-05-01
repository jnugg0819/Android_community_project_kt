package com.mamba.kt_community.data.data.DataModel

import com.mamba.kt_community.data.data.reply.Reply
import com.mamba.kt_community.response.reply.*
import io.reactivex.Observable
import io.reactivex.Single

interface DataModel {

    //MasterReply
    fun getMasterReply(boardIdx:Int): Observable<ReplyGetResponse>

    fun postMasterReply(boardIdx:Int,creatorId:String,contents:String):Single<ReplyPostResponse>

    fun updateMasterReplyThumbsUp(boardIdx:Int,masterIdx:Int,pressedId:String,isPressed:Boolean):Observable<ReplyUpdateResponse>

    fun insertMasterReplyThumbsUpUI(boardIdx: Int,masterIdx: Int,pressedId: String):Observable<ReplyUpdateResponse>

    fun deleteMasterReplyThumbsUpUI(boardIdx: Int,masterIdx: Int,pressedId: String):Observable<ReplyUpdateResponse>

    fun selectMasterReplyThumbsUpUI(boardIdx: Int,userId:String):Observable<ReplyGetUserInfoResponse>


    //SlaveReply
    fun getSlaveReply(boardIdx: Int,masterIdx: Int):Observable<ReplyGetSlaveResponse>

    fun postSlaveReply(boardIdx: Int, masterIdx: Int,creatorId: String, contents: String):Single<ReplyPostSlaveResponse>

    fun updateSlaveReplyThumbsUp(boardIdx:Int,masterIdx:Int,slaveIdx:Int,pressedId:String,isPressed:Boolean):Observable<ReplyUpdateResponse>

    fun insertSlaveReplyThumbsUpUI(boardIdx: Int,masterIdx: Int,slaveIdx:Int,pressedId: String):Observable<ReplyUpdateResponse>

    fun deleteSlaveReplyThumbsUpUI(boardIdx: Int,masterIdx: Int,slaveIdx: Int,pressedId: String):Observable<ReplyUpdateResponse>

    fun selectSlaveReplyThumbsUpUI(boardIdx: Int,masterIdx: Int,userId:String):Observable<ReplyGetSlaveUserInfoResponse>




}