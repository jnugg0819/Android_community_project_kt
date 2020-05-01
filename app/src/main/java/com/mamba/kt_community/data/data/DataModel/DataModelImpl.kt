package com.mamba.kt_community.data.data.DataModel


import com.mamba.kt_community.response.reply.*
import com.mamba.kt_community.retrofit.MyAPI
import io.reactivex.Observable
import io.reactivex.Single

class DataModelImpl(private val service:MyAPI) :DataModel{


    //MasterReply
    override fun postMasterReply(
        boardIdx: Int,
        creatorId: String,
        contents: String
    ): Single<ReplyPostResponse> {
        return service.postReply(boardIdx, creatorId, contents)
    }

    override fun getMasterReply(boardIdx: Int): Observable<ReplyGetResponse> {
        return service.getReply(boardIdx)
    }

    override fun updateMasterReplyThumbsUp(
        boardIdx: Int,
        masterIdx: Int,
        pressedId: String,
        isPressed: Boolean
    ): Observable<ReplyUpdateResponse> {
        return service.updateThumbsUp(boardIdx,masterIdx,pressedId,isPressed)
    }

    override fun insertMasterReplyThumbsUpUI(
        boardIdx: Int,
        masterIdx: Int,
        pressedId: String
    ): Observable<ReplyUpdateResponse> {
        return service.insertThumbsUpUserInfo(boardIdx,masterIdx,pressedId)
    }

    override fun deleteMasterReplyThumbsUpUI(
        boardIdx: Int,
        masterIdx: Int,
        pressedId: String
    ): Observable<ReplyUpdateResponse> {
        return service.deleteThumbsUpUserInfo(boardIdx,masterIdx,pressedId)
    }

    override fun selectMasterReplyThumbsUpUI(
        boardIdx: Int,
        userId: String
    ): Observable<ReplyGetUserInfoResponse> {
        return service.selectThumbsUpUserInfo(boardIdx,userId)
    }




    //SlaveReply
    override fun getSlaveReply(boardIdx: Int, masterIdx: Int): Observable<ReplyGetSlaveResponse> {
        return service.getReplySlave(boardIdx,masterIdx)
    }

    override fun postSlaveReply(
        boardIdx: Int,
        masterIdx: Int,
        creatorId: String,
        contents: String
    ): Single<ReplyPostSlaveResponse> {
        return service.postReplySlave(boardIdx,masterIdx,creatorId,contents)

    }

    override fun updateSlaveReplyThumbsUp(
        boardIdx: Int,
        masterIdx: Int,
        slaveIdx: Int,
        pressedId: String,
        isPressed: Boolean
    ): Observable<ReplyUpdateResponse> {
        return service.updateSlaveThumbsUp(boardIdx,masterIdx,slaveIdx,pressedId,isPressed)
    }

    override fun insertSlaveReplyThumbsUpUI(
        boardIdx: Int,
        masterIdx: Int,
        slaveIdx: Int,
        pressedId: String
    ): Observable<ReplyUpdateResponse> {
       return service.insertSlaveThumbsUpUserInfo(boardIdx,masterIdx,slaveIdx,pressedId)
    }

    override fun deleteSlaveReplyThumbsUpUI(
        boardIdx: Int,
        masterIdx: Int,
        slaveIdx: Int,
        pressedId: String
    ): Observable<ReplyUpdateResponse> {
       return service.deleteSlaveThumbsUpUserInfo(boardIdx,masterIdx,slaveIdx,pressedId)
    }

    override fun selectSlaveReplyThumbsUpUI(
        boardIdx: Int,
        masterIdx: Int,
        userId: String
    ): Observable<ReplyGetSlaveUserInfoResponse> {
        return service.selectThumbsUpSlaveUserInfo(boardIdx,masterIdx,userId)
    }

}