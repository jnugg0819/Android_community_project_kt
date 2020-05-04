package com.mamba.kt_community.Fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.mamba.kt_community.Adapter.board.BoardAdapter
import com.mamba.kt_community.BranchActivity
import com.mamba.kt_community.BranchActivity.Companion.currentUserEmail
import com.mamba.kt_community.HomeActivitty
import com.mamba.kt_community.MyPageDetailActivity

import com.mamba.kt_community.R
import com.mamba.kt_community.data.data.viewmodel.HomeViewModel
import com.mamba.kt_community.data.data.viewmodel.MyPageViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_account.*
import org.w3c.dom.Text
import kotlin.system.exitProcess

class AccountFragment : Fragment() {

    private var viewModel:MyPageViewModel?=null
    private var recyclerView: RecyclerView? = null
    var adapter: BoardAdapter?=null

    private var homeActivitty:HomeActivitty?=null

    private lateinit var mypageUserImage:CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView=inflater.inflate(R.layout.fragment_account, container, false)

        homeActivitty = activity as HomeActivitty?



        val mypageUserId=rootView.findViewById<TextView>(R.id.mypage_userId)
        mypageUserImage=rootView.findViewById<CircleImageView>(R.id.mypage_user_image)
        val mypageLogout=rootView.findViewById<TextView>(R.id.mypage_user_logout)


        mypageUserId.text=currentUserEmail

        getUserImage()

        mypageLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        homeActivitty!!.logoutLogin()
    }

        mypageUserImage.setOnClickListener {
            val intent = Intent(homeActivitty, MyPageDetailActivity::class.java)
            startActivity(intent)
        }

        viewModel=activity!!.application!!.let {
            ViewModelProvider(
                viewModelStore, ViewModelProvider.AndroidViewModelFactory(it)).get(MyPageViewModel::class.java)}

        adapter=BoardAdapter(activity!!)
        recyclerView=rootView.findViewById(R.id.mypage_recycler_view)
        val layoutManager = GridLayoutManager(activity, 1)
        recyclerView!!.layoutManager=layoutManager

        viewModel!!.getMyPage(currentUserEmail!!,recyclerView!!,adapter!!)

        return rootView
    }

    fun getUserImage(){
        //userProfileIamge
        Glide.with(activity!!.applicationContext)
            .load("http://192.168.35.27:8080/getMyPageImage?creatorId=$currentUserEmail")
            .error(R.drawable.ic_person_black_36dp)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(mypageUserImage)
    }

}
