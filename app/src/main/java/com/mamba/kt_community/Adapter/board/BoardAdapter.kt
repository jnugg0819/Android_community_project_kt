package com.mamba.kt_community.Adapter.board

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mamba.kt_community.ItemClickListener.OnBoardItemClickListener
import com.mamba.kt_community.R
import com.mamba.kt_community.data.data.board.Board
import com.mamba.kt_community.response.board.BoardReplyAllCountResponse
import com.mamba.kt_community.retrofit.MyAPI
import com.mamba.kt_community.retrofit.RetrofitClient
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class BoardAdapter(internal var context: Context) : RecyclerView.Adapter<BoardAdapter.ViewHolder>(),
    OnBoardItemClickListener {

    internal var items: MutableList<Board> = ArrayList<Board>()
    internal var listener: OnBoardItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemView = inflater.inflate(R.layout.cardview_item, viewGroup, false)
        return ViewHolder(itemView, this, context)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun replyBtnClick(viewHolder: ViewHolder, view: View, position: Int) {
        if (listener != null) {
            listener!!.replyBtnClick(viewHolder, view, position)
        }
    }

    override fun likeBtnClick(viewHolder: ViewHolder, view: View, position: Int) {
        if (listener != null) {
            listener!!.likeBtnClick(viewHolder, view, position)
        }
    }

    fun setOnItemClickListener(listener: OnBoardItemClickListener) {
        this.listener = listener
    }

    fun addItem(item: Board) {
        items.add(item)
    }

    fun setItems(boardList: ArrayList<Board>) {
        this.items = boardList
    }

    fun getItem(position: Int): Board {
        return items[position]
    }

    fun setItem(position: Int, item: Board) {
        items[position] = item
    }


    class ViewHolder(
        internal var cardView: View,
        listener: OnBoardItemClickListener?,
        context: Context
    ) : RecyclerView.ViewHolder(cardView) {

        val retrofit = RetrofitClient.instance
        private var myAPI=  retrofit!!.create(MyAPI::class.java)

        var userId: TextView = cardView.findViewById(R.id.cardview_userId)
        var contents: TextView = cardView.findViewById(R.id.cardview_contents)
        var likeCount: TextView=cardView.findViewById(R.id.cardview_like_txt)
        var creatdDatetime: TextView = cardView.findViewById(R.id.cardview_created_time)
        var userImage: CircleImageView=cardView.findViewById(R.id.cardview_user_image)
        var replyBtn: ImageButton=cardView.findViewById(R.id.cardview_reply_btn)
        var likeBtn: ToggleButton= cardView.findViewById(R.id.cardview_like_btn)
        var replyTxt: TextView=cardView.findViewById(R.id.cardview_reply_txt)
        var viewPager:ViewPager = cardView.findViewById(R.id.main_viewPger)

        //ViewPager
        private lateinit var boardViewPagerAdapter: BoardViewPagerAdapter


        init {
            replyBtn.setOnClickListener { view ->
                val position = adapterPosition
                listener?.replyBtnClick(this@ViewHolder, view, position)
            }

            likeBtn.setOnClickListener { view ->
                val position = adapterPosition
                listener?.likeBtnClick(this@ViewHolder, view, position)
            }
        }

        fun setItem(item: Board) {

            userId.text=item.creatorId
            contents.text=item.contents
            creatdDatetime.text=item.createdDatetime
            likeCount.text=(item.likeCnt).toString()


            if (item.isLikeCheck) {
                likeBtn.background = ContextCompat.getDrawable(likeBtn.context, R.drawable.ic_favorite_black_18dp)
                likeBtn.isChecked = true
            } else {
                likeBtn.isChecked = false
                likeBtn.background = ContextCompat.getDrawable(
                    likeBtn.context,
                    R.drawable.ic_favorite_border_black_18dp
                )
            }

           //user profile image
           Glide.with(cardView.context)
               .load("http://192.168.35.27:8080/getMyPageImage?creatorId=" + userId.text.toString())
               .error(R.drawable.ic_person_black_36dp)
               .diskCacheStrategy(DiskCacheStrategy.NONE)
               .skipMemoryCache(true)
               .into(userImage)



           //board image
           val urlList = ArrayList<String>()
           for (i  in item.fileList!!.indices) {
               urlList.add(
                   "http://192.168.35.27:8080/timelineGetImage?boardIdx=" + item.boardIdx + "&idx=" + item.fileList!![i].idx
               )
           }

            getAllReplyCount(item.boardIdx!!.toInt())

           boardViewPagerAdapter = BoardViewPagerAdapter(cardView.context, urlList, viewPager)
           viewPager.adapter = boardViewPagerAdapter



        }

        private fun getAllReplyCount(boardIdx:Int){
            myAPI!!.selectAllReply(boardIdx)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BoardReplyAllCountResponse>{
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(boardReplyAllCountResponse: BoardReplyAllCountResponse) {
                        replyTxt.text = boardReplyAllCountResponse.replyCount.toString()
                    }

                    override fun onError(e: Throwable) {

                    }

                })
        }
    }

}