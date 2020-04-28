package com.mamba.kt_community.Adapter.upload

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mamba.kt_community.R
import java.util.ArrayList

class UploadViewPagerAdapter(
    private val context: Context,
    imageList: ArrayList<Uri>,
    private val viewPager: ViewPager
) :
    PagerAdapter() {

    private var inflater: LayoutInflater? = null
    private var view: View? = null
    private var imageView: ImageView? = null

    private var imageList:ArrayList<Uri>?=null

    init {
        this.imageList = imageList
    }

    @SuppressLint("ResourceType")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater!!.inflate(R.layout.viewpager_image, container, false)
        imageView = view!!.findViewById(R.id.viewpager_image)

        //imageView.setImageURI(imageList.get(position));
        Glide.with(context).load(imageList?.get(position))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .error(R.raw.snake_loding)
            .placeholder(R.raw.snake_loding).into(imageView!!)

        /*Glide.with(context).load(R.raw.snake_loding).
                into(imageView);*/


        container.addView(view)

        return view as View
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun getCount(): Int {
        return imageList!!.size
    }

}