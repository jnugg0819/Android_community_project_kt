package com.mamba.kt_community

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.mamba.kt_community.Adapter.reply.SlaveReplyAdapter
import com.mamba.kt_community.ItemClickListener.OnReplySlaveItemClickListener
import com.mamba.kt_community.data.data.reply.ReplySlaveUserInfo
import com.mamba.kt_community.data.data.viewmodel.SlaveReplyViewModel
import kotlinx.android.synthetic.main.activity_slave_reply.*
import kotlinx.android.synthetic.main.slave_reply_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class SlaveReplyActivity : AppCompatActivity() , SwipeRefreshLayout.OnRefreshListener{

    //koin 슬레이브 뷰 모델
    val viewModel:SlaveReplyViewModel by viewModel()
    //리사이클러뷰
    private lateinit var recyclerView: RecyclerView
    //slave 어댑터
    private lateinit var slaveReplyAdapter: SlaveReplyAdapter
    //현재 유저정보
    private lateinit var currentUserEmail:String

    //Master에서온 Intent데이터
    private lateinit var boardIdx: String
    private lateinit var masterIdx: String
    private lateinit var contents: String
    private lateinit var createdDatetime: String
    private lateinit var thumbsUp: String
    private lateinit var thumbsDown: String
    private lateinit var creatorId: String

    companion object{
        //따봉누른 유저정보
        var userInfoList = ArrayList<ReplySlaveUserInfo>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slave_reply)

        //인텐트 데이터 받아오기
        intentDataFromMaster()

        //현재 유저 정보 받아오기
        if(FirebaseAuth.getInstance().currentUser!=null){
            currentUserEmail= FirebaseAuth.getInstance().currentUser!!.email!!
        }

        slaveReplyAdapter= SlaveReplyAdapter(this)

        slave_swipe_layout.setOnRefreshListener(this)
        slave_swipe_layout.setColorSchemeResources(R.color.colorIris)

        //슬레이브 리사이클러뷰 설정
        slave_reply_recycler_view.run {
            adapter=slaveReplyAdapter
            layoutManager=GridLayoutManager(this@SlaveReplyActivity,1)
        }

        //게시물 번호 ,마스터 게시물번호로 댓글가져오기
        viewModel.getSlaveReply(boardIdx.toInt(),masterIdx.toInt())

        //Livedata로 Observe
        viewModel.slaveReplyGetLiveData.observe(this, Observer {
            slaveReplyAdapter.setItems(it.response)
            slaveReplyAdapter.notifyDataSetChanged()
        })

        slave_reply_send.visibility= View.INVISIBLE
        slave_reply_send.requestFocus()
        //자판 나오게하기
        val imm: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

        slave_reply_typing.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                slave_reply_send.visibility=View.VISIBLE
                if((slave_reply_typing.text.toString()).isEmpty()){
                    slave_reply_send.visibility=View.INVISIBLE
                }
            }

        })

        slaveReplyAdapter.setOnItemClickListener(object :OnReplySlaveItemClickListener{
            override fun onItemClick(
                viewHolder: SlaveReplyAdapter.ViewHolder,
                view: View,
                position: Int
            ) {

            }

            override fun thumbsUpClick(
                viewHolder: SlaveReplyAdapter.ViewHolder,
                view: View,
                position: Int
            ) {
                val on = (view as ToggleButton).isChecked
                val item = slaveReplyAdapter.getItem(position)
                val boardIdx = item.boardIdx
                val masterIdx = item.masterIdx
                val slaveIdx = item.slaveIdx

                val textView = viewHolder.itemView.findViewById<TextView>(R.id.slave_reply_thumbs_up_text)

                if (on) {
                    var plus = Integer.parseInt(textView.text.toString())
                    plus += 1
                    textView.text = plus.toString()
                    view.setBackground(
                        ContextCompat.getDrawable(
                            view.getContext(),
                            R.drawable.icons8_thumbs_up_black_24
                        )
                    )
                    viewModel.updateSlaveReplyThumbsUp(boardIdx, masterIdx, slaveIdx, currentUserEmail,true)//따봉 +1
                    viewModel.insertSlaveReplyThumbsUpUI(boardIdx, masterIdx, slaveIdx,currentUserEmail)//따봉누른 유저정보 집어넣기
                } else {
                    var minus = Integer.parseInt(textView.text.toString())
                    minus -= 1
                    textView.text = minus.toString()
                    view.setBackground(
                        ContextCompat.getDrawable(
                            view.getContext(),
                            R.drawable.icons8_thumbs_up_white_24
                        )
                    )
                    viewModel.updateSlaveReplyThumbsUp(boardIdx, masterIdx, slaveIdx,currentUserEmail, false)//따봉 -1
                    viewModel.deleteSlaveReplyThumbsUpUI(boardIdx, masterIdx, slaveIdx,currentUserEmail)//따봉누른 유저정보 삭제
                }
            }

        })

        //따봉누른 유저정보 가져오기
        viewModel.selectSlaveReplyThumbsUpUI(boardIdx.toInt(),masterIdx.toInt(),currentUserEmail)

        slave_reply_send.setOnClickListener {
            viewModel.postSlaveReply(boardIdx.toInt(),masterIdx.toInt(),currentUserEmail,slave_reply_typing.text.toString())
        }


    }

    private fun intentDataFromMaster() {
        val intent = intent
        boardIdx = intent.getStringExtra("boardIdx")!!
        masterIdx = intent.getStringExtra("masterIdx")!!
        contents = intent.getStringExtra("contents")!!
        createdDatetime = intent.getStringExtra("createdDatetime")!!
        creatorId = intent.getStringExtra("creatorId")!!
        thumbsUp = intent.getStringExtra("thumbsUp")!!

        slave_reply_fixed_idAndTime.text= "$creatorId*$createdDatetime"
        slave_reply_fixed_reply.text=contents
        slave_reply_fixed_thumbs_up_text.text=thumbsUp

        Glide.with(this).load("http://192.168.35.30:8080/getMyPageImage?creatorId=$creatorId")
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(slave_reply_fixed_image)

    }


    override fun onRefresh() {
        viewModel.getSlaveReply(boardIdx.toInt(),masterIdx.toInt())
        viewModel.selectSlaveReplyThumbsUpUI(boardIdx.toInt(),masterIdx.toInt(),currentUserEmail)
        slave_swipe_layout.isRefreshing = false
    }
}
