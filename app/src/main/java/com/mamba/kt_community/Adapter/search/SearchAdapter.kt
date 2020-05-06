package com.mamba.kt_community.Adapter.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mamba.kt_community.ItemClickListener.OnSearchItemClickListener
import com.mamba.kt_community.R
import com.mamba.kt_community.data.data.board.Board
import java.util.ArrayList

class SearchAdapter(context: Context) :
RecyclerView.Adapter<SearchAdapter.ViewHolder>(), OnSearchItemClickListener{

    private var items: ArrayList<Board> = ArrayList<Board>()
    private var listener:OnSearchItemClickListener?=null

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater=LayoutInflater.from(viewGroup.context)
        val view=inflater.inflate(R.layout.search_item,viewGroup,false)
        return ViewHolder(view,this)
    }

    override fun onBindViewHolder(viewHolder: SearchAdapter.ViewHolder, position: Int) {
        val item=items[position]
        viewHolder.setItem(item)

    }

    fun setOnItemClickListener(listener: OnSearchItemClickListener){
        this.listener=listener
    }

    override fun searchItemClick(viewHolder: ViewHolder, view: View, position: Int) {
      if(listener!=null){
          listener!!.searchItemClick(viewHolder,view,position)
      }
    }

    override fun getItemCount(): Int {
        return items.size
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

    fun clearData(){
        this.items.clear()
    }


    fun setItem(position: Int, item: Board) {
        items[position] = item
    }


    class ViewHolder(private var searchView: View,listener:OnSearchItemClickListener ):RecyclerView.ViewHolder(searchView){

        private val searchImage=searchView.findViewById<ImageView>(R.id.search_item_image)
        private val searchTitle=searchView.findViewById<TextView>(R.id.search_item_title)
        private val searchDate=searchView.findViewById<TextView>(R.id.search_item_date)
        private val searchLikeCnt=searchView.findViewById<TextView>(R.id.search_item_like_cnt)
        //private val searchlyt=searchView.findViewById<LinearLayout>(R.id.search_item_lyt)

        init {
           /* searchlyt.setOnClickListener { view->
                val position=adapterPosition
                listener.searchItemClick(this@ViewHolder,view,position)
            }*/

        }



        fun setItem(item:Board){


            searchTitle.text=item.title
            searchDate.text=item.createdDatetime
            searchLikeCnt.text= item.likeCnt.toString()

            //user profile image
            Glide.with(searchView.context)
                .load("http://192.168.35.27:8080/timelineGetImage?boardIdx=" + item.boardIdx +"&idx="+ item.fileList!![0].idx)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(searchImage)

        }

    }





}