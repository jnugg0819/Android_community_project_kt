package com.mamba.kt_community

import android.content.Context
import android.content.Intent
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.youtube.player.internal.i
import com.google.android.youtube.player.internal.j
import com.google.firebase.auth.FirebaseAuth
import com.mamba.kt_community.Adapter.reply.MasterReplyAdapter
import com.mamba.kt_community.ItemClickListener.OnReplyItemClickListener
import com.mamba.kt_community.base.BaseViewModel
import com.mamba.kt_community.data.data.reply.ReplyUserInfo
import com.mamba.kt_community.data.data.viewmodel.MasterReplyViewModel
import kotlinx.android.synthetic.main.activity_master_reply.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class MasterReplyActivity : AppCompatActivity(),SwipeRefreshLayout.OnRefreshListener {

    //koin inject
    val viewModel:MasterReplyViewModel by viewModel()

    private lateinit var masterReplyAdapter:MasterReplyAdapter

    //현재 유저정보
    private lateinit var currentUserEmail:String

    //해당게시물
    private lateinit var boardIdx: String


    companion object{
        //따봉 누른 유저정보 가져오기
        var userInfoList = ArrayList<ReplyUserInfo>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master_reply)

        //화면리프레시시 같이 클리어
        userInfoList.clear()

        //마스터어댑터 초기화
        masterReplyAdapter=MasterReplyAdapter(this)

        //SwipeRefresh
        master_swipe_layout.setOnRefreshListener(this)
        master_swipe_layout.setColorSchemeResources(R.color.colorIris)

        if(FirebaseAuth.getInstance().currentUser!=null){
            currentUserEmail= FirebaseAuth.getInstance().currentUser!!.email!!
        }

        //인텐트로 게시물 번호 가져오기
        val fromHome=intent
        boardIdx=fromHome.getStringExtra("boardIdx")

        //마스터 리사이클러뷰 설정
        master_reply_recycler_view.run {
            adapter=masterReplyAdapter
            layoutManager=GridLayoutManager(this@MasterReplyActivity,1)
        }

        //따봉누른 유저정보 가져오기
        viewModel.selectMasterReplyThumbsUpUI(boardIdx.toInt(),currentUserEmail)

        //게시물 번호로 해당 게시물 댓글 가져오기
        viewModel.getMasterReply(boardIdx.toInt())


        //Livedata로 Observe해주기
        viewModel.masterReplyGetLiveData.observe(this, Observer {

            if(userInfoList.size!=0){
                for(i in it.response.indices){
                    for(j in userInfoList.indices){
                        if(it.response[i].masterIdx== userInfoList[j].masterIdx){
                            it.response[i].isThumbsUpCheck=true
                        }
                    }
                }
            }

            masterReplyAdapter.setItems(it.response)
            masterReplyAdapter.notifyDataSetChanged()
        })

        //전송버튼 가시화 유무 텍스트가있으면 VISIBLE
        master_reply_send.visibility= View.INVISIBLE
        master_reply_typing.requestFocus()

        //자판 나오게하기
        val imm:InputMethodManager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY)

        //텍스트 변화시 설정
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

        //마스터 댓글 아이템클릭
        masterReplyAdapter.setOnItemClickListener(object :OnReplyItemClickListener{
            //슬레이브 댓글로
            override fun onItemClick(
                viewHolder: MasterReplyAdapter.ViewHolder,
                view: View,
                position: Int
            ) {
                val item=masterReplyAdapter.getItem(position)
                val boardIdx = item.boardIdx
                val masterIdx = item.masterIdx
                val contents = item.contents
                val createdDatetime = item.createdDatetime
                val creatorId = item.creatorId
                val thumbsUp = item.thumbsUp

                val sIntent = Intent(this@MasterReplyActivity, SlaveReplyActivity::class.java)
                sIntent.putExtra("boardIdx", boardIdx.toString())
                sIntent.putExtra("masterIdx", masterIdx.toString())
                sIntent.putExtra("contents", contents)
                sIntent.putExtra("createdDatetime", createdDatetime)
                sIntent.putExtra("thumbsUp", thumbsUp.toString())
                sIntent.putExtra("creatorId", creatorId)
                startActivity(sIntent)

            }

            //좋아요 누르기
            override fun thumbsUpClick(
                viewHolder: MasterReplyAdapter.ViewHolder,
                view: View,
                position: Int
            ) {
                val on = (view as ToggleButton).isChecked
                val item = masterReplyAdapter.getItem(position)
                val boardIdx = item.boardIdx
                val masterIdx = item.masterIdx

                val textView = viewHolder.itemView.findViewById<TextView>(R.id.master_reply_thumbs_up_text)

                //눌렀으면 +1
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
                    viewModel.updateMasterReplyThumbsUp(boardIdx, masterIdx, currentUserEmail,true)//따봉 +1
                    viewModel.insertMasterReplyThumbsUpUI(boardIdx, masterIdx,currentUserEmail)//따봉누른 유저정보 집어넣기
                } else {//아니면 -1
                    var minus = Integer.parseInt(textView.text.toString())
                    minus -= 1
                    textView.text = minus.toString()
                    view.setBackground(
                        ContextCompat.getDrawable(
                            view.getContext(),
                            R.drawable.icons8_thumbs_up_white_24
                        )
                    )
                    viewModel.updateMasterReplyThumbsUp(boardIdx, masterIdx, currentUserEmail,false)//따봉 -1
                    viewModel.deleteMasterReplyThumbsUpUI(boardIdx, masterIdx,currentUserEmail)//따봉누른 유저정보 삭제
                }


            }

        })



        //서버로 댓글 전송
        master_reply_send.setOnClickListener {
            viewModel.postMasterReply(boardIdx.toInt(),currentUserEmail,master_reply_typing.text.toString())
        }


    }

    //댓글 업데이트
    override fun onRefresh() {
        viewModel.selectMasterReplyThumbsUpUI(boardIdx.toInt(),currentUserEmail)
        viewModel.getMasterReply(boardIdx.toInt())
        master_swipe_layout.isRefreshing=false
    }
}
