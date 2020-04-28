package com.mamba.kt_community

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import com.mamba.kt_community.Adapter.reply.MasterReplyAdapter
import com.mamba.kt_community.base.BaseViewModel
import com.mamba.kt_community.data.data.viewmodel.MasterReplyViewModel
import kotlinx.android.synthetic.main.activity_master_reply.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MasterReplyActivity : AppCompatActivity(),SwipeRefreshLayout.OnRefreshListener {

    //koin inject
    val viewModel:MasterReplyViewModel by viewModel()

    private val masterReplyAdapter:MasterReplyAdapter=MasterReplyAdapter(this)

    //현재 유저정보
    private lateinit var currentUserEmail:String

    //해당게시물
    private lateinit var boardIdx: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master_reply)

        master_swipe_layout.setOnRefreshListener(this)
        master_swipe_layout.setColorSchemeResources(R.color.colorIris)

        if(FirebaseAuth.getInstance().currentUser!=null){
            currentUserEmail= FirebaseAuth.getInstance().currentUser!!.email!!
        }

        //get boardIdx
        val fromHome=intent
        boardIdx=fromHome.getStringExtra("boardIdx")

        master_reply_recycler_view.run {
            adapter=masterReplyAdapter
            layoutManager=GridLayoutManager(this@MasterReplyActivity,1)
        }

        viewModel.getMasterReply(boardIdx.toInt())

        viewModel.masterReplyGetLiveData.observe(this, Observer {
            masterReplyAdapter.setItems(it.response)
            masterReplyAdapter.notifyDataSetChanged()
        })

        master_reply_send.visibility= View.INVISIBLE
        master_reply_typing.requestFocus()
        val imm:InputMethodManager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY)

        master_reply_typing.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                master_reply_send.visibility=View.VISIBLE
                if((master_reply_typing.text.toString()).isEmpty()){
                    master_reply_send.visibility=View.INVISIBLE
                }
            }
        })

        master_reply_send.setOnClickListener {
            viewModel.postMasterReply(boardIdx.toInt(),currentUserEmail,master_reply_typing.text.toString())
        }


    }


    override fun onRefresh() {
        viewModel.getMasterReply(boardIdx.toInt())
        master_swipe_layout.isRefreshing=false
    }
}
