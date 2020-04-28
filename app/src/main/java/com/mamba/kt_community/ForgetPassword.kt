package com.mamba.kt_community

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_register.*

class ForgetPassword : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        forget_pd_send_btn.isEnabled=false

        forget_pd_id_edt.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(charSequence.toString().isNotEmpty() && charSequence.toString().contains("@") && charSequence.toString().toString().contains("."))
                {
                    forget_pd_send_btn.isEnabled =true
                    forget_pd_id_lyt.error=""
                }else{
                    forget_pd_send_btn.isEnabled=false
                    forget_pd_id_lyt.error="이메일을 제대로 입력해주세요"
                }
            }
        })

        forget_pd_send_btn.setOnClickListener {
            FirebaseAuth.getInstance().sendPasswordResetEmail(forget_pd_id_edt.text.toString())

            val inputMethodManager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken,0)

            Snackbar.make(forget_pd_root_lyt,"${forget_pd_id_edt.text} 가서 확인해주세요",Snackbar.LENGTH_INDEFINITE)
                .setAction("확인", View.OnClickListener { finish() })
                .show()
        }


    }
}
