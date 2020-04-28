package com.mamba.kt_community.retrofit

import YoutubeSeachRoot
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeAPI {


    @GET("youtube/v3/search")
    fun selectSearch(@Query("part") part: String,
                     @Query("q") q:String,
                     @Query("key") key:String,
                     @Query("maxResults")maxResults:Int
    ): Observable<YoutubeSeachRoot>

}