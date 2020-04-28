package com.mamba.kt_community.data.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.mamba.kt_community.Adapter.board.BoardAdapter
import com.mamba.kt_community.Fragment.AccountFragment
import com.mamba.kt_community.HomeActivitty
import com.mamba.kt_community.MyPageDetailActivity
import com.mamba.kt_community.data.data.mypage.MyPageInfo
import com.mamba.kt_community.response.board.BoardResponse
import com.mamba.kt_community.response.mypage.MyPageResponse
import com.mamba.kt_community.retrofit.MyAPI
import com.mamba.kt_community.retrofit.RetrofitClient
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MyPageViewModel:ViewModel(){

    private val retrofit=RetrofitClient.instance
    private val myAPI=retrofit!!.create(MyAPI::class.java)

    fun getMyPage(creatorId:String ,recyclerView: RecyclerView, adapter: BoardAdapter){
        myAPI.getMypage(creatorId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<BoardResponse>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(boardResponse: BoardResponse) {
                        val newBoard = boardResponse.response
                        adapter.setItems(newBoard)
                        adapter.notifyDataSetChanged()
                }
                override fun onComplete() {
                    recyclerView.adapter = adapter

                }

                override fun onError(e: Throwable) {
                }

            })
    }

    fun postMyPageInfo(
        existImage: Boolean,
        imageRealPath: String,
        userId: String,
        nickname: String,
        artist: String,
        music: String,
        myPageDetailActivity: MyPageDetailActivity
        ) {

        val part: MultipartBody.Part

        val image = File(imageRealPath)
        val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), image)
        part = MultipartBody.Part.createFormData("myPageImage", image.name, requestFile)

        val requestUserId = RequestBody.create(MediaType.parse("Multipart/form-data"), userId)
        val requestNickname = RequestBody.create(
            MediaType.parse("Multipart/form-data"),
            nickname
        )
        val requestArtist = RequestBody.create(
            MediaType.parse("Multipart/form-data"),
            artist
        )
        val requestMusic = RequestBody.create(
            MediaType.parse("Multipart/form-data"),
            music
        )

        myAPI.postMyPageInfo(existImage,part,requestUserId,requestNickname,requestArtist,requestMusic).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<MyPageResponse>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(mypageResponse: MyPageResponse) {
                   if (mypageResponse.response.equals("update")) {
                        Log.d("postImage",mypageResponse.response)

                    } else {
                       Log.d("postImage",mypageResponse.response)
                   }
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    HomeActivitty.accountFragment!!.getUserImage()
                    myPageDetailActivity.finish()
                }

            })

    }


    fun getMyPageText(
        creatorId: String
    ){

        myAPI.getMyPageText(creatorId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<MyPageInfo>{
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(myPageInfo: MyPageInfo) {

                }

                override fun onError(e: Throwable) {

                }

            })

    }



}