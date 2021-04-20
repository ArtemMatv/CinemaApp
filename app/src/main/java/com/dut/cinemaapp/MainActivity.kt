package com.dut.cinemaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private var textList = mutableListOf<String>()
    private var titles = mutableListOf<String>()
    lateinit var pager: ViewPager2
    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postToList()

        pager =  findViewById(R.id.view_pager2)
        tabLayout = findViewById(R.id.tabLayout)

        pager.adapter = ViewPagerAdapter(textList)
        pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(tabLayout, pager) {
            tab, position -> tab.text = titles[position]
        }.attach()
    }

    private fun addToList(text: String, title: String){
        textList.add(text)
        titles.add(title)
    }

    private fun postToList(){
        for (i in 1..5){
            addToList("$i", "Tab number #$i")
        }
    }
}