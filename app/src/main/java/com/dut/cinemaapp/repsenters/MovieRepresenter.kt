package com.dut.cinemaapp.repsenters

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dut.cinemaapp.R
import com.dut.cinemaapp.adapters.ReviewsAdapter
import com.dut.cinemaapp.domain.Movie
import com.dut.cinemaapp.domain.Review
import com.dut.cinemaapp.services.MoviesService
import com.dut.cinemaapp.services.ReviewsService
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepresenter(
    private val id: Long,
    private val context: Context,
    private val movieTrailer: YouTubePlayerView,
    private val moviePoster: ImageView,
    private val movieTitle: TextView,
    private val movieDuration: TextView,
    private val movieGenres: TextView,
    private val movieDescription: TextView,
    private val movieActors: TextView,
    private val movieCountry: TextView,
    private val reviews_recycler: RecyclerView,
    private val reviewLabel: TextView
) {

    lateinit var movie: Movie
    lateinit var reviews: List<Review>

    fun loadData() {
        MoviesService().getMovie(id).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    movie = response.body() as Movie
                    setMovieData()
                    loadReviews()
                } else
                    Toast.makeText(
                        context,
                        "Error " + response.code().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Toast.makeText(context, "Error while getting movie", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun loadReviews() {
        ReviewsService().getComments(movie.id).enqueue(object : Callback<List<Review>> {
            override fun onResponse(
                call: Call<List<Review>>,
                response: Response<List<Review>>
            ) {
                if (response.isSuccessful) {
                    reviews = response.body() as List<Review>
                    setReviewsData()
                } else
                    Toast.makeText(
                        context,
                        "Error " + response.code().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
            }

            override fun onFailure(call: Call<List<Review>>, t1: Throwable) {
                Toast.makeText(
                    context,
                    "Error while getting reviews",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setMovieData() {
        movieTrailer.initialize(
            context.getString(R.string.youtubeApi),
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider?,
                    player: YouTubePlayer?,
                    wasRestored: Boolean
                ) {
                    when {
                        player == null -> return
                        wasRestored -> player.play()
                        else -> {
                            player.cueVideo(movie.trailerPath)
                            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                        }
                    }
                }

                override fun onInitializationFailure(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubeInitializationResult?
                ) {
                    movieTrailer.layoutParams.height = 0
                }
            })

        Picasso.get().load(movie.posterPath).into(moviePoster)
        movieTitle.text = movie.title

        var h = (movie.duration / 3600).toString()
        var min = (movie.duration % 3600) / 60
        movieDuration.text = h + "h " + min + "min"

        var genres = ""
        movie.genres.forEach { e -> genres += e.name + ", " }

        if (genres.endsWith(", "))
            genres = genres.substring(0, genres.length - 2)

        movieGenres.text = genres

        movieDescription.text = movie.description
        movieActors.text = movie.actors
        movieCountry.text = movie.country
    }

    private fun setReviewsData() {
        if (reviews.count() > 0)
            reviewLabel.text = "Reviews:"
        else
            reviewLabel.text = "No reviews for now"

        reviews_recycler.adapter = ReviewsAdapter(reviews)
        reviews_recycler.layoutManager = LinearLayoutManager(context)
    }
}