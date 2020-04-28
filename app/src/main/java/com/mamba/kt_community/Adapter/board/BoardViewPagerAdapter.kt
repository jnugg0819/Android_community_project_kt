package com.mamba.kt_community.Adapter.board

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.mamba.kt_community.R
import java.util.ArrayList

class BoardViewPagerAdapter(
    private val mContext: Context,
    internal var data: ArrayList<String>,
    private val viewPager: ViewPager
) :
    PagerAdapter() {
    private var inflater: LayoutInflater? = null
    private var imageView: ImageView? = null
    private var view: View?=null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater!!.inflate(R.layout.viewpager_image, container, false)
        imageView = view!!.findViewById(R.id.viewpager_image)

        Glide.with(mContext).load(data[position]).into(imageView!!)
        container.addView(view)

        return view as View
    }

    override fun getCount(): Int {
        return data.size
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)

    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as View
    }


}