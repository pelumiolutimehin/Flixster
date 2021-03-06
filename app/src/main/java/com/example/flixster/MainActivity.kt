package com.example.flixster

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity"
private const val NOW_PLAYING_URL ="https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"

class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMovies = findViewById(R.id.rvMovies)

        movieAdapter = MovieAdapter(this, this, movies, resources.configuration)
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "on Failure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess: JSON data $json")
                try{
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie list $movies")
                } catch (e: JSONException){
                    Log.e(TAG, "Encountered exception $e")
                }

            }

        })
    }

    fun launchComposeView(taskItem: String, position: Int) {
        val i = Intent(this@MainActivity, DetailActivity::class.java)
        i.putExtra("task", taskItem)
        i.putExtra("position", position)
        startActivityForResult(i, 20)
    }
}