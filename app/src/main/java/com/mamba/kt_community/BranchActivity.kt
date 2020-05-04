package com.mamba.kt_community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_branch.*

class BranchActivity : AppCompatActivity() {

    companion object{
        lateinit var currentUserEmail:String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch)

        //userEmail
        if(FirebaseAuth.getInstance().currentUser!=null){
          currentUserEmail = FirebaseAuth.getInstance().currentUser!!.email!!
        }

        moveHomePage()

        branch_email_login_lyt.setOnClickListener{view->
            val intent= Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
        }

        branch_register_btn.setOnClickListener{view->
            val intent= Intent(applicationContext,RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun moveHomePage(){
        var currentUser= FirebaseAuth.getInstance().currentUser
            if(currentUser!=null){
            startActivity(Intent(this,HomeActivitty::class.java))
        }
    }
}
