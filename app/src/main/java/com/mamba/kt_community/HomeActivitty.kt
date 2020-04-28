package com.mamba.kt_community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.mamba.kt_community.Fragment.AccountFragment
import com.mamba.kt_community.Fragment.HomeFragment
import com.mamba.kt_community.Fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.system.exitProcess

class HomeActivitty : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object{
        var homeFragment = HomeFragment()
        var searchFragment: SearchFragment? = null
        var accountFragment: AccountFragment? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction().replace(R.id.home_container, homeFragment)
            .commit()

        home_bottom_navigation.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tab1 -> {
                if (homeFragment == null) {
                    homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction().add(R.id.home_container, homeFragment)
                        .commit()
                }
                if (homeFragment != null) supportFragmentManager.beginTransaction().show(
                    homeFragment
                ).commit()
                if (searchFragment != null) supportFragmentManager.beginTransaction().hide(
                    searchFragment!!
                ).commit()
                if (accountFragment != null) supportFragmentManager.beginTransaction().hide(
                    accountFragment!!
                ).commit()
            }
            R.id.tab2 -> {
                if (searchFragment == null) {
                    searchFragment = SearchFragment()
                    supportFragmentManager.beginTransaction()
                        .add(R.id.home_container, searchFragment!!).commit()
                }
                if (searchFragment != null) supportFragmentManager.beginTransaction().show(
                    searchFragment!!
                ).commit()
                if (homeFragment != null) supportFragmentManager.beginTransaction().hide(
                    homeFragment
                ).commit()
                if (accountFragment != null) supportFragmentManager.beginTransaction().hide(
                    accountFragment!!
                ).commit()
            }
            R.id.tab3 -> {
                if (accountFragment == null) {
                    accountFragment = AccountFragment()
                    supportFragmentManager.beginTransaction()
                        .add(R.id.home_container, accountFragment!!).commit()
                }
                if (accountFragment != null) supportFragmentManager.beginTransaction().show(
                    accountFragment!!
                ).commit()
                if (homeFragment != null) supportFragmentManager.beginTransaction().hide(
                    homeFragment
                ).commit()
                if (searchFragment != null) supportFragmentManager.beginTransaction().hide(
                    searchFragment!!
                ).commit()
            }
        }
        return true
    }

    fun logoutLogin() {
        this@HomeActivitty.finishAffinity()
        val intent = Intent(this@HomeActivitty, BranchActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }
}
