package com.mamba.kt_community

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.mamba.kt_community.Adapter.upload.UploadViewPagerAdapter
import com.mamba.kt_community.response.upload.UploadResponse
import com.mamba.kt_community.retrofit.MyAPI
import com.mamba.kt_community.retrofit.RetrofitClient
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_upload.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.ArrayList

class UploadActivity : AppCompatActivity() {

    var genreItems = arrayOf("한국/아이돌", "한국/랩&힙합", "한국/OST&발라드", "해외")
    var selectedItem: String?=null

    //Request code
    private val PICK_IMAGE_MULTIPLE = 111
    private val PICK_SEARCH_YOUTUBE_REQUEST = 501
    private val RECORD_REQUEST_CODE=1000

    //Retrofit
    private var myAPI:MyAPI?=null

    //Dialog
    private var progressDialog: ProgressDialog?=null
    private var dialog: AlertDialog? = null

    //앨범에서 가지고온 image Uri List
    private var imageUriList:ArrayList<Uri> = ArrayList()
    //앨뱀에서 가지고온 image 실제경로
    private var imageRealPath = ArrayList<String>()

    //현재 사용자 아이디 가져오기
    private var currentUserEmail:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        if(FirebaseAuth.getInstance().currentUser!=null){
            currentUserEmail=FirebaseAuth.getInstance().currentUser!!.email
        }


        val retrofit = RetrofitClient.instance
        myAPI = retrofit!!.create(MyAPI::class.java)

        upload_imageview_txt.setOnClickListener {
            setUpPermissions()
        }

        upload_gallery_btn.setOnClickListener {
            setUpPermissions()
        }

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            genreItems
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        upload_spinner.adapter=adapter

        upload_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                selectedItem = genreItems[i]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        upload_get_youtube_txtv.setOnClickListener {
            var intent=Intent(applicationContext,UploadSearchYoutubeActivity::class.java)
            startActivityForResult(intent,PICK_SEARCH_YOUTUBE_REQUEST)
        }

        upload_share_btn.setOnClickListener {
            progressDialog = ProgressDialog(this)
            progressDialog!!.setMessage("업로드 중....")
            progressDialog!!.show()
            uploadDataBase()
        }
    }

    private fun setUpPermissions(){
        val permission=ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if(permission!=PackageManager.PERMISSION_GRANTED){
            Log.i("permission","Permission to record denied")
            makeRequest()
        }else{
            openGallery()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),RECORD_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            RECORD_REQUEST_CODE->{
                if(grantResults.isEmpty()&& grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"권한 거부됨",Toast.LENGTH_LONG).show()
                }else{
                    openGallery()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun openGallery() {
        imageRealPath.clear()
        imageUriList.clear()
        val intent=Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent,"사진을 선택해주세요"),PICK_IMAGE_MULTIPLE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode==PICK_SEARCH_YOUTUBE_REQUEST){
            val videoId=data?.getStringExtra("videoId")
            upload_get_youtube_txtv.text = videoId
        }

        if(requestCode== PICK_IMAGE_MULTIPLE){
            upload_imageview_txt.visibility=View.INVISIBLE
            if(data==null){
                Toast.makeText(
                    this,
                    "아무것도 선택하지 않으셨습니다.",
                    Toast.LENGTH_LONG
                ).show()
            }else{
                if(data.clipData==null){
                    Toast.makeText(
                        this,
                        "다중선택 불가한 기기입니다.",
                        Toast.LENGTH_LONG
                    ).show()
                }else{
                    val clipData=data.clipData
                    when {
                        clipData!!.itemCount>9 -> Toast.makeText(
                            this,
                            "사진은 9장까지 선택가능합니다",
                            Toast.LENGTH_LONG
                        ).show()
                        clipData.itemCount == 1 -> {
                            imageUriList.add(clipData.getItemAt(0).uri)
                            imageRealPath.add(getPath(clipData.getItemAt(0).uri))
                        }
                        clipData.itemCount in 2..9 -> for(i in 0 until clipData.itemCount){
                            imageUriList.add(clipData.getItemAt(i).uri)
                            imageRealPath.add(getPath(clipData.getItemAt(i).uri))
                        }
                    }
                }
                val uploadViewPagerAdapter =
                    UploadViewPagerAdapter(
                        this,
                        imageUriList,
                        upload_viewPager
                    )
                upload_viewPager.adapter = uploadViewPagerAdapter
            }
        }

        if (requestCode == PICK_SEARCH_YOUTUBE_REQUEST) {
            val videoId:String? = data?.getStringExtra("videoId")
            upload_get_youtube_txtv.text = videoId
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getPath(uri: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, proj, null, null, null)
        cursor!!.moveToNext()
        val path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
        val resultUri = Uri.fromFile(File(path))
        return path
    }

    private fun uploadDataBase() {

        var part: MultipartBody.Part
        val listMultipartBody = ArrayList<MultipartBody.Part>()

        for (i in imageRealPath.indices) {
            val image = File(imageRealPath[i])
            val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), image)
            part = MultipartBody.Part.createFormData("uploadImage", image.name, requestFile)
            listMultipartBody.add(part)
        }

        val requestUserId = RequestBody.create(MediaType.parse("Multipart/form-data"), currentUserEmail)
        val requestGenre = RequestBody.create(MediaType.parse("Multipart/form-data"), selectedItem)
        val requestYoutubeAdd = RequestBody.create(
            MediaType.parse("Multipart/form-data"),
            upload_get_youtube_txtv.text.toString()
        )
        val requestTitle =
            RequestBody.create(MediaType.parse("Multipart/form-data"), upload_title_txt.text.toString())
        val requesetMusicName = RequestBody.create(
            MediaType.parse("Multipart/form-data"),
            upload_music_name_txt.text.toString()
        )
        val requestSingerName = RequestBody.create(
            MediaType.parse("Multipart/form-data"),
            upload_singer_name_txt.text.toString()
        )

        val requestRelatedSong = RequestBody.create(
            MediaType.parse("Multipart/form-data"),
            upload_singer_related_songs_txt.text.toString()
        )

        val requestContents = RequestBody.create(
            MediaType.parse("Multipart/form-data"),
            upload_contents_txt.text.toString()
        )


        myAPI!!.insertUpload(
            requestUserId,
            requestGenre,
            requestYoutubeAdd,
            requestTitle,
            requesetMusicName,
            requestSingerName,
            requestRelatedSong,
            requestContents,
            listMultipartBody
        ).subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<UploadResponse> {
                override fun onSubscribe(d: Disposable) {

                }
                override fun onNext(uploadResponse: UploadResponse) {
                    if (uploadResponse.response) {
                        progressDialog!!.dismiss()
                        val builder = AlertDialog.Builder(applicationContext)
                        dialog = builder.setMessage("업로드 완료").setPositiveButton("확인", null).create()
                        dialog!!.show()
                        val intent = Intent(applicationContext, HomeActivitty::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else {
                        val builder = AlertDialog.Builder(applicationContext)
                        dialog = builder.setMessage("업로드 실패!.").setNegativeButton("확인", null).create()
                        dialog!!.show()
                    }

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()

                }

                override fun onComplete() {

                }
            })
    }
}
