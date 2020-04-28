package com.mamba.kt_community

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import com.google.android.gms.common.util.IOUtils.toByteArray
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Base64
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class LoginActivity : AppCompatActivity() {

    var googleSignInClient:GoogleSignInClient?=null
    val RC_SIGN_IN=1000
    var callbackManager=CallbackManager.Factory.create()

    private var dialog: AlertDialog? = null

    override fun onResume() {
        super.onResume()
        moveHomePage()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Glide.with(this).load(R.raw.music).circleCrop().into(login_image)

        var gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient=GoogleSignIn.getClient(this,gso)

        login_forget_password.setOnClickListener {
            startActivity(Intent(this,ForgetPassword::class.java))
        }

        login_login_btn.setOnClickListener {
            loginEmail()
        }

        login_google_btn.setOnClickListener {
            var singInIntent=googleSignInClient?.signInIntent
            startActivityForResult(singInIntent,RC_SIGN_IN)
        }

        login_facebook_btn.setOnClickListener {
            facebookLogin()
        }

        printHashKey(this)
    }

    fun moveHomePage(){
        var currentUser=FirebaseAuth.getInstance().currentUser
        if(currentUser!=null){
            startActivity(Intent(this,HomeActivitty::class.java))
        }
    }

    fun facebookLogin(){
        LoginManager.getInstance().loginBehavior=LoginBehavior.WEB_VIEW_ONLY
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"))
        LoginManager.getInstance().registerCallback(callbackManager,object:FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                firebaseAuthWithFacebook(result)
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {

            }

        })

    }

    fun firebaseAuthWithFacebook(result:LoginResult?){
        var credential=FacebookAuthProvider.getCredential(result?.accessToken?.token!!)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {task ->
            if(task.isSuccessful){
                moveHomePage()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode,resultCode,data)
        if(requestCode==RC_SIGN_IN){
            var task=GoogleSignIn.getSignedInAccountFromIntent(data)
            var account=task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        }
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        var credential=GoogleAuthProvider.getCredential(acct?.idToken,null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                moveHomePage()
            }
        }

    }

    private fun loginEmail(){
        var email=login_id.text.toString()
        var password=login_password.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).
            addOnCompleteListener {task ->
            if(task.isSuccessful){
                moveHomePage()
            }else {
               AlertDialog.Builder(this)
                   .setTitle("안내")
                   .setMessage("아이디와 비밀번호를 다시 확인해주세요")
                   .setPositiveButton("확인",null)
                   .show()
            }


        }
    }

    fun printHashKey(pContext: Context) {
        try {
            val info = pContext.getPackageManager()
                .getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                println("printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {

        } catch (e: Exception) {

        }

    }


}
