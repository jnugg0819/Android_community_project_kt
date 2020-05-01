package com.mamba.kt_community.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.ArrayList

class HomeFmTabPagerAdapter(private val fm: FragmentManager, private val count: Int) :
    FragmentStatePagerAdapter(fm) {

    private val tabTitle = arrayOf("인기", "최신", "한국/아이돌", "한국/랩&힙합","한국/OST&발라드","해외")


    private var items = ArrayList<Fragment>()

    fun addItem(item: Fragment) {
        items.add(item)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Fragment {
        return items[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitle[position]
    }
}