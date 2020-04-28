package com.mamba.kt_community

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*
import org.w3c.dom.Text

class RegisterActivity : AppCompatActivity() {

    var textLengthChk=false
    var textLengthChk2=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        Glide.with(this).load(R.raw.music).circleCrop().into(register_image)

        register_register_btn.isEnabled=false

        register_id.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(charSequence.toString().isNotEmpty() && charSequence.toString().contains("@") && charSequence.toString().toString().contains("."))
                {
                    textLengthChk =true
                    register_id_lyt.error=""
                }else{
                    textLengthChk=false
                    register_id_lyt.error="이메일을 제대로 입력해주세요"
                }
                checkAllValidate()
            }
        })
        register_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                if (charSequence.length >= 6) {
                    textLengthChk2=true
                    register_password_lyt.error=""
                } else {
                    textLengthChk2=false
                    register_password_lyt.error="비밀번호는 6자 이상입니다."
                }
                checkAllValidate()
            }
            override fun afterTextChanged(editable: Editable) {

            }
        })

        register_register_btn.setOnClickListener {
            createEmailId()
        }

    }

    fun checkAllValidate(){
        register_register_btn.isEnabled = textLengthChk && textLengthChk2
    }

    fun createEmailId() {
        var email = register_id.text.toString()
        var password = register_password.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this,BranchActivity::class.java))
                }else{
                    AlertDialog.Builder(this)
                        .setMessage("다른아이디와 중복됩니다.")
                        .setPositiveButton("확인",DialogInterface.OnClickListener{ dialog,which->
                            null
                        }).show()
                }
            }
    }


}
