package com.dut.cinemaapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dut.cinemaapp.adapters.ActualSessionsAdapter
import com.dut.cinemaapp.adapters.AllMoviesAdapter
import com.dut.cinemaapp.adapters.DataUpdatable
import kotlinx.android.synthetic.main.item_page.view.*

class ViewPagerAdapter(private var amount: Int) :
    RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {
    private lateinit var activityContext: Context

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recycler: RecyclerView = itemView.recycler
        val swipe: SwipeRefreshLayout = itemView.swipeRefresh
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        val holder = Pager2ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_page,
                    parent,
                    false
                )
        )

        activityContext = parent.context
        holder.recycler.layoutManager = LinearLayoutManager(parent.context)
        holder.recycler.setHasFixedSize(true)

        return holder
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        if (position == 0){
            holder.recycler.adapter = ActualSessionsAdapter(mutableListOf())
        }
        else if (position == 1){
            holder.recycler.adapter = AllMoviesAdapter(mutableListOf())
        }


        holder.swipe.setOnRefreshListener {
            (holder.recycler.adapter as DataUpdatable).updateData(holder, activityContext)
        }

        (holder.recycler.adapter as DataUpdatable).updateData(holder, activityContext)
    }

    override fun getItemCount(): Int {
        return amount
    }
}