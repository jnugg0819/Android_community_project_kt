package com.mamba.kt_community.Fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mamba.kt_community.Adapter.HomeFmTabPagerAdapter
import com.mamba.kt_community.Fragment.InnerHome.*
import com.mamba.kt_community.HomeActivitty

import com.mamba.kt_community.R
import com.mamba.kt_community.UploadActivity
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private val innerHomeFirst = InnerHomeFirst()
    private val innerHomeSecond=InnerHomeSecond()
    private val innerHomeThird=InnerHomeThird()
    private val innerHomeFourth= InnerHomeFourth()
    private val innerHomeFifth=InnerHomeFifth()
    private val innerHomeSixth= InnerHomeSixth()


    //메인 액티비티
    private var homeAtivity: HomeActivitty? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        homeAtivity = activity as HomeActivitty?

        var homeTabPager = rootView.findViewById(R.id.home_fm_pager_content) as ViewPager
        var tabLayout = rootView.findViewById(R.id.home_fm_layout_tab) as TabLayout

        tabLayout.addTab(tabLayout.newTab().setText("인기"))
        tabLayout.addTab(tabLayout.newTab().setText("최신"))
        tabLayout.addTab(tabLayout.newTab().setText("한국/아이돌"))
        tabLayout.addTab(tabLayout.newTab().setText("한국/랩&힙합"))
        tabLayout.addTab(tabLayout.newTab().setText("한국/OST&발라드"))
        tabLayout.addTab(tabLayout.newTab().setText("해외"))

        val homeFmTabPagerAdapter =
            HomeFmTabPagerAdapter(homeAtivity!!.supportFragmentManager, tabLayout.tabCount)

        homeFmTabPagerAdapter.addItem(innerHomeFirst)
        homeFmTabPagerAdapter.addItem(innerHomeSecond)
        homeFmTabPagerAdapter.addItem(innerHomeThird)
        homeFmTabPagerAdapter.addItem(innerHomeFourth)
        homeFmTabPagerAdapter.addItem(innerHomeFifth)
        homeFmTabPagerAdapter.addItem(innerHomeSixth)

        homeTabPager.adapter = homeFmTabPagerAdapter
        homeTabPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                homeTabPager.currentItem = tab!!.position
            }

        })

        val fab: View = rootView.findViewById(R.id.home_fm_fa_btn)

        fab.setOnClickListener {
            startActivity(Intent(homeAtivity, UploadActivity::class.java))
        }

        return rootView
    }



}
