package com.mamba.kt_community.retrofit

import com.mamba.kt_community.data.data.mypage.MyPageInfo
import com.mamba.kt_community.data.data.reply.Reply
import com.mamba.kt_community.response.board.BoardLikeGetUserInfoResponse
import com.mamba.kt_community.response.board.BoardLikeUpdateResponse
import com.mamba.kt_community.response.board.BoardReplyAllCountResponse
import com.mamba.kt_community.response.board.BoardResponse
import com.mamba.kt_community.response.mypage.MyPageResponse
import com.mamba.kt_community.response.mypage.MyPageTextResponse
import com.mamba.kt_community.response.reply.*
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

    @PUT("uploadBoard/likeUpdate")
    @FormUrlEncoded
    fun updateLike(
        @Field("boardIdx") boardIdx: Int, @Field("pressedId") pressedId: String, @Field(
            "isPressed"
        ) isPressed: Boolean
    ): Observable<BoardLikeUpdateResponse>

    @GET("uploadBoard/getReplyCount")
    fun selectAllReply(@Query("boardIdx") boardIdx: Int): Observable<BoardReplyAllCountResponse>

    @POST("timeLineLikePostUserInfo")
    @FormUrlEncoded
    fun insertLikeUserInfo(@Field("boardIdx") boardIdx: Int, @Field("pressedId") pressedId: String): Observable<BoardLikeUpdateResponse>

    @DELETE("timeLineLikeDeleteUserInfo")
    fun deleteLikeUserInfo(@Query("boardIdx") boardIdx: Int, @Query("pressedId") pressedId: String): Observable<BoardLikeUpdateResponse>

    @GET("timeLineLikeGetUserInfo")
    fun selectLikeUserInfo(@Query("userId") userId: String): Observable<BoardLikeGetUserInfoResponse>


    //MyPage
    @GET("mypage")
    fun getMypage(@Query("creatorId") creatorId:String):Observable<BoardResponse>

    @GET("getMyPageText")
    fun getMyPageText(@Query("creatorId") creatorId: String): Observable<MyPageTextResponse>

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

    @PUT("replyUpdate")
    @FormUrlEncoded
    fun updateThumbsUp(
        @Field("boardIdx") boardIdx: Int, @Field("masterIdx") masterIdx: Int, @Field(
            "pressedId"
        ) pressedId: String, @Field("isPressed") isPressed: Boolean
    ): Observable<ReplyUpdateResponse>

    @POST("replyPostUserInfo")
    @FormUrlEncoded
    fun insertThumbsUpUserInfo(
        @Field("boardIdx") boardIdx: Int, @Field("masterIdx") masterIdx: Int, @Field(
            "pressedId"
        ) pressedId: String
    ): Observable<ReplyUpdateResponse>

    @DELETE("replyDeleteUserInfo")
    fun deleteThumbsUpUserInfo(
        @Query("boardIdx") boardIdx: Int, @Query("masterIdx") masterIdx: Int, @Query(
            "pressedId"
        ) pressedId: String
    ): Observable<ReplyUpdateResponse>

    @GET("replyGetUserInfo")
    fun selectThumbsUpUserInfo(@Query("boardIdx") boardIdx: Int, @Query("userId") userId: String): Observable<ReplyGetUserInfoResponse>


    //SlaveReply
    @GET("getReplySlave")
    fun getReplySlave(@Query("boardIdx") boardIdx: Int, @Query("masterIdx") masterIdx: Int): Observable<ReplyGetSlaveResponse>

    @POST("postReplySlave")
    @FormUrlEncoded
    fun postReplySlave(
        @Field("boardIdx") boardIdx: Int, @Field("masterIdx") masterIdx: Int, @Field("creatorId") creatorId: String,
        @Field("contents") contents: String
    ): Single<ReplyPostSlaveResponse>

    @GET("replyGetSlaveUserInfo")
    fun selectThumbsUpSlaveUserInfo(
        @Query("boardIdx") boardIdx: Int, @Query("masterIdx") masterIdx: Int, @Query(
            "userId"
        ) userId: String
    ): Observable<ReplyGetSlaveUserInfoResponse>

    @PUT("replySlaveUpdate")
    @FormUrlEncoded
    fun updateSlaveThumbsUp(
        @Field("boardIdx") boardIdx: Int, @Field("masterIdx") masterIdx: Int, @Field(
            "slaveIdx"
        ) slaveIdx: Int, @Field("pressedId") pressedId: String, @Field("isPressed") isPressed: Boolean
    ): Observable<ReplyUpdateResponse>

    @POST("replySlavePostUserInfo")
    @FormUrlEncoded
    fun insertSlaveThumbsUpUserInfo(
        @Field("boardIdx") boardIdx: Int, @Field("masterIdx") masterIdx: Int, @Field(
            "slaveIdx"
        ) slaveIdx: Int, @Field("pressedId") pressedId: String
    ): Observable<ReplyUpdateResponse>

    @DELETE("replySlaveDeleteUserInfo")
    fun deleteSlaveThumbsUpUserInfo(
        @Query("boardIdx") boardIdx: Int, @Query("masterIdx") masterIdx: Int, @Query(
            "slaveIdx"
        ) slaveIdx: Int, @Query("pressedId") pressedId: String
    ): Observable<ReplyUpdateResponse>


}