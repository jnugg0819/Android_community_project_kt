package com.mamba.kt_community

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.mamba.kt_community.data.data.viewmodel.MyPageViewModel
import kotlinx.android.synthetic.main.activity_my_page_detail.*
import java.io.File

class MyPageDetailActivity : AppCompatActivity() {

    companion object{
        private val PICK_IMAGE_REQUEST_DETAILE = 101



    }

    //사진 실제 경로
    private var imageRealPath: String? = null

    //bitmap
    private var bitmap: Bitmap? = null

    //ViewModel
    private lateinit var viewModel:MyPageViewModel

    //현재 사용자 아이디 가져오기
    private var currentUserEmail: String?=null


    //사진존재여부 확인
    var existImage: Boolean?=null


    var checkId:String?=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_detail)

        if(FirebaseAuth.getInstance().currentUser!=null){
            currentUserEmail= FirebaseAuth.getInstance().currentUser!!.email!!
        }

        viewModel=this.application.let {
            ViewModelProvider(
            viewModelStore, ViewModelProvider.AndroidViewModelFactory(it)).get(MyPageViewModel::class.java)}

        //userProfileIamge
        Glide.with(this)
            .load("http://192.168.35.50:8080/getMyPageImage?creatorId=$currentUserEmail")
            .error(R.drawable.ic_person_black_36dp)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(mypage_detail_image)

        compareImage()

        mypage_detail_image.setOnClickListener {
            detailOpenGallery()
        }

        mypage_de_complete_button.setOnClickListener {
             viewModel.postMyPageInfo(existImage!!,imageRealPath!!,currentUserEmail!!,mypage_detail_nickname.text.toString(), mypage_detail_fa_artirst.text.toString(),mypage_detail_fa_music.text.toString(),this)
        }
    }


    private fun compareImage(){

        val drawable=getDrawable(R.drawable.ic_person_black_36dp)
        existImage = drawable != mypage_detail_image.drawable

    }

    private fun detailOpenGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type=MediaStore.Images.Media.CONTENT_TYPE
        //intent.type = "image/*"
        //intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"사진을 선택해주세요"), PICK_IMAGE_REQUEST_DETAILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_DETAILE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val filePath = data.data
            try {
                imageRealPath = getPath(filePath)
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                mypage_detail_image.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun getPath(uri: Uri?): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri!!, proj, null, null, null)
        cursor!!.moveToNext()
        val path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
        val resultUri = Uri.fromFile(File(path))
        return path
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
