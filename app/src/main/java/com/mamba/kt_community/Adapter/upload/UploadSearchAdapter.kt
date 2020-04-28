package com.mamba.kt_community.Adapter.upload

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide

import com.mamba.kt_community.ItemClickListener.OnUploadSearchItem
import com.mamba.kt_community.R
import com.mamba.kt_community.data.data.Youtube

import java.util.ArrayList

class UploadSearchAdapter(var context: Context) : RecyclerView.Adapter<UploadSearchAdapter.ViewHolder>(),
    OnUploadSearchItem {

    private var items: MutableList<Youtube> = ArrayList<Youtube>()
    private var listener: OnUploadSearchItem? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemView = inflater.inflate(R.layout.youtube_list_item, viewGroup, false)
        return ViewHolder(
            itemView,
            this,
            context
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun itemClick(viewHolder: ViewHolder, view: View, position: Int) {
        if(listener!=null){
            listener!!.itemClick(viewHolder,view,position)
        }
    }

    override fun itemLongClick(viewHolder: ViewHolder, view: View, position: Int) {
        if(listener!=null){
            listener!!.itemLongClick(viewHolder,view,position)
        }
    }

    fun setOnItemClickListener(listener: OnUploadSearchItem) {
        this.listener = listener
    }

    fun addItem(item: Youtube) {
        items.add(item)
    }

    fun setItems(boardList: ArrayList<Youtube>) {
        this.items = boardList
    }

    fun getItem(position: Int): Youtube {
        return items[position]
    }

    fun setItem(position: Int, item: Youtube) {
        items[position] = item
    }


    class ViewHolder(
        var listView: View,
        listener: OnUploadSearchItem?,
        context: Context
    ) : RecyclerView.ViewHolder(listView) {


        var title: TextView = listView.findViewById(R.id.you_list_title_txtv)
        var date: TextView = listView.findViewById(R.id.you_list_date_txtv)
        var image: ImageView = listView.findViewById(R.id.you_list_img)
        var rootLyt:LinearLayout=listView.findViewById(R.id.you_list_root_lyt)

        init {

            rootLyt.setOnClickListener{ view->
                val position=adapterPosition
                listener?.itemClick(this@ViewHolder,view,position)
            }

            rootLyt.setOnLongClickListener { view->
                val position=adapterPosition
                listener?.itemLongClick(this@ViewHolder,view,position)
                return@setOnLongClickListener true
            }

        }



        fun setItem(item: Youtube) {

            title.text = item.title
            date.text=item.publishedAt

            //user profile image
            Glide.with(listView.context)
                .load(item.url)
                .into(image)
        }
    }

}