package com.dut.cinemaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var titles = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postToList()

        view_pager2.adapter = ViewPagerAdapter(titles.size)
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(tabLayout, view_pager2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun postToList() {
        titles.add("Actual sessions")
        titles.add("All movies")
    }
}