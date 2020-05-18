package com.mamba.kt_community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.mamba.kt_community.BranchActivity.Companion.currentUserEmail
import com.mamba.kt_community.Fragment.AccountFragment
import com.mamba.kt_community.Fragment.HomeFragment
import com.mamba.kt_community.Fragment.SearchFragment
import com.mamba.kt_community.data.data.board.BoardLikeUserInfo
import com.mamba.kt_community.data.data.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import java.util.ArrayList
import kotlin.system.exitProcess

class HomeActivitty : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {


        //좋아요 누른 사람들 정보(ViewModel에서 접근)
        lateinit var userInfoList: ArrayList<BoardLikeUserInfo>
    }

    var homeFragment = HomeFragment()
    var searchFragment :SearchFragment?=null
    var accountFragment :AccountFragment?=null

    private var viewModel: HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = this.application!!.let {
            ViewModelProvider(
                viewModelStore, ViewModelProvider.AndroidViewModelFactory(it)
            ).get(HomeViewModel::class.java)
        }

        //좋아요 유저정보 불러오기
        viewModel!!.getLikeUserInfo(currentUserEmail)

        supportFragmentManager.beginTransaction().replace(R.id.home_container, homeFragment)
            .commit()

        home_bottom_navigation.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tab1 -> {
                if(homeFragment==null){
                    homeFragment= HomeFragment()
                    supportFragmentManager.beginTransaction().add(R.id.home_container, homeFragment).commit()
                }
                supportFragmentManager.beginTransaction().show(homeFragment).commit()
                supportFragmentManager.beginTransaction().hide(searchFragment!!).commit()
                supportFragmentManager.beginTransaction().hide(accountFragment!!).commit()
            }
            R.id.tab2 -> {
                if(searchFragment==null){
                    searchFragment= SearchFragment()
                    supportFragmentManager.beginTransaction().add(R.id.home_container, searchFragment!!).commit()
                }
                supportFragmentManager.beginTransaction().hide(homeFragment).commit()
                supportFragmentManager.beginTransaction().show(searchFragment!!).commit()
                if(accountFragment!=null) supportFragmentManager.beginTransaction().hide(accountFragment!!).commit()
            }
            R.id.tab3 -> {
                if(accountFragment==null){
                    accountFragment= AccountFragment()
                    supportFragmentManager.beginTransaction().add(R.id.home_container, accountFragment!!).commit()
                }
                supportFragmentManager.beginTransaction().hide(homeFragment).commit()
                if(searchFragment!=null) supportFragmentManager.beginTransaction().hide(searchFragment!!).commit()
                supportFragmentManager.beginTransaction().show(accountFragment!!).commit()
            }
        }
        return true
    }

    fun logoutLogin() {
        //this@HomeActivitty.finishAffinity()
        val intent = Intent(this@HomeActivitty, BranchActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
