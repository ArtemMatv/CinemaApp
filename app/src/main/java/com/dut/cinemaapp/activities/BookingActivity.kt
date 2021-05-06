package com.dut.cinemaapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dut.cinemaapp.R
import com.dut.cinemaapp.adapters.PlaceAdapter
import com.dut.cinemaapp.adapters.SelectedPlacesAdapter
import com.dut.cinemaapp.domain.*
import com.dut.cinemaapp.services.SessionsService
import com.dut.cinemaapp.services.TicketsService
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.place.*
import kotlinx.android.synthetic.main.tool_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class BookingActivity : AppCompatActivity() {
    var session: Session? = null
    var tickets: SessionTicketsList? = null
    var chosenTickets: MutableList<Place> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        tool_bar_title.text = ""
        setActionBar(tool_bar)

        tool_bar_btn.setOnClickListener { onBackPressed() }

        toolbar_acc_btn.setOnClickListener {
            this.startActivity(Intent(this, AccountActivity::class.java))
        }

        getSessionData()

        booking_chosen_places.adapter = SelectedPlacesAdapter(chosenTickets)
        booking_chosen_places.layoutManager = LinearLayoutManager(this)

        booking_btn.setOnClickListener {
            TicketsService().bookTickets(session?.id!!, chosenTickets).enqueue(object: Callback<List<Ticket>>{
                override fun onResponse(
                    call: Call<List<Ticket>>,
                    response: Response<List<Ticket>>
                ) {
                    if (response.isSuccessful)
                    {
                        this@BookingActivity.startActivity(Intent(this@BookingActivity, AccountActivity::class.java))
                    }
                    else {
                        Toast.makeText(
                            this@BookingActivity,
                            "Error: " + response.code().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<Ticket>>, t: Throwable) {
                    Toast.makeText(
                        this@BookingActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        }
    }

    private fun getSessionData() {
        val id: Long = intent.getLongExtra("sessionId", -1L)

        SessionsService().getSession(id).enqueue(object : Callback<Session> {
            override fun onResponse(call: Call<Session>, response: Response<Session>) {
                if (response.isSuccessful) {
                    session = response.body()
                    SessionsService().getSessionTickets(id)
                        .enqueue(object : Callback<SessionTicketsList> {
                            @SuppressLint("ClickableViewAccessibility")
                            override fun onResponse(
                                call: Call<SessionTicketsList>,
                                response: Response<SessionTicketsList>
                            ) {
                                tickets = response.body()

                                booking_movie_title.text = session?.movieTitle
                                booking_session_hall.text = session?.hallName
                                booking_session_date.text = SimpleDateFormat("HH:mm\nd MMM")
                                    .format(
                                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                            .parse(session?.date!!)!!
                                    )

                                booking_places.numColumns = tickets?.place!!

                                booking_places.adapter = PlaceAdapter(
                                    this@BookingActivity,
                                    tickets!!.tickets,
                                    tickets!!.rowsAmount,
                                    tickets!!.place,
                                    chosenTickets
                                ) { booking_chosen_places.adapter?.notifyDataSetChanged() }

                                booking_places.layoutParams = LinearLayout.LayoutParams(
                                    booking_places.width,
                                    tickets?.rowsAmount!! * 80,
                                )
                            }

                            override fun onFailure(call: Call<SessionTicketsList>, t: Throwable) {
                                Toast.makeText(
                                    this@BookingActivity,
                                    t.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })
                }
            }

            override fun onFailure(call: Call<Session>, t: Throwable) {
                Toast.makeText(
                    this@BookingActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}