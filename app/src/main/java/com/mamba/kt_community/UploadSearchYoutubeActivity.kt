package com.mamba.kt_community

import YoutubeSeachRoot
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mamba.kt_community.Adapter.upload.UploadSearchAdapter
import com.mamba.kt_community.ItemClickListener.OnUploadSearchItem
import com.mamba.kt_community.data.data.Youtube
import com.mamba.kt_community.retrofit.RetrofitClientYoutube
import com.mamba.kt_community.retrofit.YoutubeAPI
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_upload_search_youtube.*
import java.util.*

class UploadSearchYoutubeActivity : AppCompatActivity() {

    //Retrofit
    private var myAPI: YoutubeAPI?=null

    //View
    private var searchEt: EditText? = null
    private var recyclerView: RecyclerView? = null

    //Youtube data
    private var sdata = ArrayList<Youtube>()

    //adapter
    private var adapter: UploadSearchAdapter?=null

    //Youtube API KEY
    private val API_KEY = "AIzaSyA-Ar8uZATzfcDd6mSvJktcT1Ex1hgQn6w"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_search_youtube)

        adapter= UploadSearchAdapter(this)

        val retrofit = RetrofitClientYoutube.youtubeInstance
        myAPI = retrofit.create(YoutubeAPI::class.java)

        recyclerView=findViewById(R.id.you_se_recycler_view)
        val layoutManager = GridLayoutManager(this, 1)
        recyclerView!!.layoutManager=layoutManager

        adapter!!.setOnItemClickListener(object:OnUploadSearchItem{
            override fun itemLongClick(
                viewHolder: UploadSearchAdapter.ViewHolder,
                view: View,
                position: Int
            ) {
                val item=adapter!!.getItem(position)

                AlertDialog.Builder(this@UploadSearchYoutubeActivity)
                    .setTitle("안내")
                    .setMessage("하나만 선택해주세요")
                    .setPositiveButton("선택",DialogInterface.OnClickListener{
                         dialog, which ->
                            val intent=Intent(applicationContext,UploadActivity::class.java)
                            intent.putExtra("videoId", item.videoId)
                            setResult(Activity.RESULT_OK,intent)
                            finish()

                    })
                    .setNegativeButton("취소",DialogInterface.OnClickListener{
                          dialog, which ->


                    })
                    .show()
            }

            override fun itemClick(
                viewHolder: UploadSearchAdapter.ViewHolder,
                view: View,
                position: Int
            ) {
                val item= adapter!!.getItem(position)
                val intent:Intent=Intent(applicationContext,UploadSearchYoutubePlayerActivity::class.java)
                intent.putExtra("videoId",item.videoId)
                startActivity(intent)
            }
        })





        searchEt = findViewById<EditText>(R.id.you_se_search_edt)


        you_se_search_btn.setOnClickListener {
            sdata.clear()
            searchResult()
        }

    }

    var videoId:String?=null

    private fun searchResult() {
        myAPI?.selectSearch("snippet",searchEt?.text.toString(),API_KEY,50)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<YoutubeSeachRoot>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(youtubeSearchRoot: YoutubeSeachRoot) {
                    for (i in youtubeSearchRoot.items.indices) {

                        val kind=youtubeSearchRoot.items[i].id.kind
                        if(kind=="youtube#video"){
                            videoId=youtubeSearchRoot.items[i].id.videoId
                        }

                        val title=youtubeSearchRoot.items[i].snippet.title.replace("#39;".toRegex(), "\'")
                            .replace("&quot;".toRegex(), "\"").replace(
                                "&amp;".toRegex(), "&"
                            )
                        val date=youtubeSearchRoot.items[i].snippet.publishedAt.substring(0, 10)
                        val imageUrl=youtubeSearchRoot.items[i].snippet.thumbnails.default.url
                        sdata.add(
                            Youtube(
                                videoId,
                                title,
                                imageUrl,
                                date
                            )
                        )
                    }
                    adapter?.setItems(sdata)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    recyclerView!!.adapter=adapter
                }

            })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
