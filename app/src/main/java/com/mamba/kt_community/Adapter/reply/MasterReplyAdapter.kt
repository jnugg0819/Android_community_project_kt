package com.mamba.kt_community.Adapter.reply

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mamba.kt_community.ItemClickListener.OnReplyItemClickListener
import com.mamba.kt_community.R
import com.mamba.kt_community.data.data.reply.Reply
import com.mamba.kt_community.data.data.reply.ReplyUserInfo
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList

class MasterReplyAdapter(var context: Context) :
    RecyclerView.Adapter<MasterReplyAdapter.ViewHolder>(), OnReplyItemClickListener {
    private var items = ArrayList<Reply>()
    var userItems = ArrayList<ReplyUserInfo>()
    var listener: OnReplyItemClickListener? = null

    lateinit var view: View

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        view = inflater.inflate(R.layout.master_reply_item, viewGroup, false)
        return ViewHolder(view, this)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnItemClickListener(listener: OnReplyItemClickListener) {
        this.listener = listener

    }

    //to slave
    override fun onItemClick(holder: ViewHolder, view: View, position: Int) {
        if (listener != null) {
            listener!!.onItemClick(holder, view, position)
        }
    }

    override fun thumbsUpClick(viewHolder: ViewHolder, view: View, position: Int) {
        if (listener != null) {
            listener!!.thumbsUpClick(viewHolder, view, position)
        }
    }

    fun addItem(item: Reply) {
        items.add(item)
    } //아이템 추가해주기

    fun setItems(items: ArrayList<Reply>) {

        this.items = items

    }  //아이템들 SET해주기 (전체)


    fun getItem(position: Int): Reply {
        return items[position]
    } //아이템들 가져오기

    fun setItem(position: Int, item: Reply) {
        items[position] = item
    } //해당 아이템 수정해주기!!

    class ViewHolder(private var replyView: View, listener: OnReplyItemClickListener?) :
        RecyclerView.ViewHolder(replyView) {

        private var userImage: CircleImageView = replyView.findViewById(R.id.master_reply_image)
        private var userIdAndTime: TextView = replyView.findViewById(R.id.master_reply_idAndTime)
        private var contents: TextView = replyView.findViewById(R.id.master_reply_reply)
        private var thumbsUp: ToggleButton = replyView.findViewById(R.id.master_reply_thumbs_up)
        private var thumbsUpTxt: TextView = replyView.findViewById(R.id.master_reply_thumbs_up_text)
        private var replyToSlave: TextView = replyView.findViewById(R.id.master_reply_to_slave)

        init {
            replyToSlave.setOnClickListener { view ->
                val position = adapterPosition
                if (listener != null) {
                    listener.onItemClick(this@ViewHolder, view, position)
                }
            }

            thumbsUp.setOnClickListener { view ->
                val position = adapterPosition
                if (listener != null) {
                    listener.thumbsUpClick(this@ViewHolder, view, position)
                }
            }
        }


        fun setItem(item: Reply) {
            userIdAndTime.text = item.creatorId + " " + item.createdDatetime
            contents.text = item.contents
            thumbsUpTxt.text=(item.thumbsUp).toString()

            if (item.isThumbsUpCheck) {
                thumbsUp.background = ContextCompat.getDrawable(
                    thumbsUp.context,
                    R.drawable.icons8_thumbs_up_black_24
                )
                thumbsUp.isChecked = true
            } else {
                thumbsUp.isChecked = false
                thumbsUp.background = ContextCompat.getDrawable(
                    thumbsUp.context,
                    R.drawable.icons8_thumbs_up_white_24
                )
            }


            Glide.with(replyView.context)
                .load("http://192.168.35.27:8080/getMyPageImage?creatorId=" + item.creatorId)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(userImage)
        }


    }
}