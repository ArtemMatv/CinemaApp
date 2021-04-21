package com.dut.cinemaapp.adapters

import android.content.Context
import com.dut.cinemaapp.ViewPagerAdapter

interface DataUpdatable {
    fun updateData(holder: ViewPagerAdapter.Pager2ViewHolder, context: Context)
}