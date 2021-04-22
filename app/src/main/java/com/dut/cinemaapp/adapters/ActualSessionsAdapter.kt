package com.dut.cinemaapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dut.cinemaapp.R
import com.dut.cinemaapp.domain.Session
import com.dut.cinemaapp.interfaces.DataUpdatable
import com.dut.cinemaapp.services.SessionsService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.session_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class ActualSessionsAdapter(private val sessionsList: List<Session>) :
    RecyclerView.Adapter<ActualSessionsAdapter.ActualSession>(), DataUpdatable {

    private lateinit var activityContext: Context

    inner class ActualSession(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.session_image
        val movieTitle: TextView = itemView.session_title
        val date: TextView = itemView.session_date
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActualSession {
        activityContext = parent.context
        return ActualSession(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.session_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ActualSession, position: Int) {
        val currentItem = sessionsList[position]

        Picasso.get().load(currentItem.moviePoster).into(holder.imageView)
        holder.movieTitle.text = currentItem.movieTitle

        holder.date.text = SimpleDateFormat("HH:mm\nd MMM")
            .format(
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .parse(currentItem.date)
            )

    }

    override fun getItemCount(): Int {
        return sessionsList.size
    }

    override fun updateData(holder: ViewPagerAdapter.Pager2ViewHolder) {
        SessionsService().getSessions().enqueue(object : Callback<List<Session>> {
            @SuppressLint("ShowToast")
            override fun onFailure(call: Call<List<Session>>?, t: Throwable?) {
                Toast.makeText(activityContext, t?.message, Toast.LENGTH_SHORT).show()
                holder.swipe.isRefreshing = false
            }

            @SuppressLint("ShowToast")
            override fun onResponse(
                call: Call<List<Session>>?,
                response: Response<List<Session>>?
            ) {
                if (response?.isSuccessful!!)
                    holder.recycler.adapter =
                        ActualSessionsAdapter(response.body() as MutableList<Session>)

                holder.swipe.isRefreshing = false
            }
        })
    }
}