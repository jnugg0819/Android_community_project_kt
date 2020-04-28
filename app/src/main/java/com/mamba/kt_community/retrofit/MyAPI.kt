package com.mamba.kt_community.retrofit

import com.mamba.kt_community.data.data.mypage.MyPageInfo
import com.mamba.kt_community.data.data.reply.Reply
import com.mamba.kt_community.response.board.BoardResponse
import com.mamba.kt_community.response.mypage.MyPageResponse
import com.mamba.kt_community.response.reply.ReplyGetResponse
import com.mamba.kt_community.response.reply.ReplyPostResponse
import com.mamba.kt_community.response.upload.UploadResponse
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

interface MyAPI {


    //Upload
    @Multipart
    @POST("uploadBoard")
    fun insertUpload(
        @Part("creatorId") creatorId: RequestBody, @Part("musicCategory") musicCategory: RequestBody, @Part(
            "youtubeAddress"
        ) youtubeAddress: RequestBody,
        @Part("title") title: RequestBody, @Part("musicName") musicName: RequestBody,
        @Part("singerName") singerName: RequestBody, @Part("relatedSong") relatedSong: RequestBody,
        @Part("contents") contents: RequestBody, @Part file: ArrayList<MultipartBody.Part>
    ): Observable<UploadResponse>


    //TimeLine
    @GET("timelinefirst")
    fun getTimelineFirst(): Observable<BoardResponse>

    @GET("timelinesecond")
    fun getTimelineSecond(): Observable<BoardResponse>

    @GET("timelinethird")
    fun getTimelineThird(): Observable<BoardResponse>

    @GET("timelinefourth")
    fun getTimelineFourth(): Observable<BoardResponse>

    @GET("timelinefifth")
    fun getTimelineFifth(): Observable<BoardResponse>

    @GET("timelinesixth")
    fun getTimelineSixth(): Observable<BoardResponse>

    //MyPage
    @GET("mypage")
    fun getMypage(@Query("creatorId") creatorId:String):Observable<BoardResponse>

    @GET("getMyPageText")
    fun getMyPageText(@Query("creatorId") creatorId: String): Observable<MyPageInfo>

    //ScalarsCOnverterFactory사용
    @Multipart
    @POST("postMyPageInfo")
    fun postMyPageInfo(
        @Part("existImage") existImage: Boolean, @Part file: MultipartBody.Part,
        @Part("mypageId") mypageId: RequestBody, @Part("mypageNickname") mypageNickname: RequestBody, @Part(
            "mypageSinger"
        ) mypageSinger: RequestBody,
        @Part("mypageMusic") mypageMusic: RequestBody
    ): Observable<MyPageResponse>


    //MasterReply
    @POST("replyPost")
    @FormUrlEncoded
    fun postReply(
        @Field("boardIdx") boardIdx: Int, @Field("creatorId") creatorId: String,
        @Field("contents") contents: String
    ): Single<ReplyPostResponse>

    @GET("replyGet")
    fun getReply(@Query("boardIdx") boardIdx: Int): Observable<ReplyGetResponse>


}