package com.dut.cinemaapp.activities

import android.os.Bundle
import android.widget.Toast
import com.dut.cinemaapp.R
import com.dut.cinemaapp.domain.Review
import com.dut.cinemaapp.dto.review.NewReview
import com.dut.cinemaapp.repsenters.MovieRepresenter
import com.dut.cinemaapp.services.ReviewsService
import com.google.android.youtube.player.YouTubeBaseActivity
import kotlinx.android.synthetic.main.movie_layout.*
import kotlinx.android.synthetic.main.review_create.*
import kotlinx.android.synthetic.main.reviews_layout.*
import kotlinx.android.synthetic.main.tool_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieActivity : YouTubeBaseActivity() {

    var id: Long = -1
    private lateinit var movieRepresenter: MovieRepresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        tool_bar.title = ""
        setActionBar(tool_bar)

        btn_back.setOnClickListener { onBackPressed() }

        id = intent.extras?.getLong("id")!!

        movieRepresenter = MovieRepresenter(
            id,
            this,
            movieTrailer,
            moviePoster,
            movieTitle,
            movieDuration,
            movieGenres,
            movieDescription,
            movieActors,
            movieCountry,
            tool_bar_title,
            reviews_recycler,
            reviewLabel
        )

        movieRepresenter.loadData()

        reviewCreateButton.setOnClickListener {
            ReviewsService().createReview(NewReview(reviewCreateText.text.toString(), id, -1))
                .enqueue(
                    object :
                        Callback<Review> {
                        override fun onResponse(call: Call<Review>, response: Response<Review>) {
                            if (response.isSuccessful)
                                movieRepresenter.loadReviews()
                            else
                                Toast.makeText(
                                    this@MovieActivity,
                                    response.code().toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                        }

                        override fun onFailure(call: Call<Review>, t: Throwable) {
                            Toast.makeText(
                                this@MovieActivity,
                                t.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
        }
    }
}