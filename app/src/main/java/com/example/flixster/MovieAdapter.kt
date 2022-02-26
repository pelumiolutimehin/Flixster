package com.example.flixster

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private const val TAG = "MovieAdapter"

class MovieAdapter(private val context: Context, private val movies: List<Movie>, private val config: Configuration)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val tvTitleLand = itemView.findViewById<TextView>(R.id.tvTitleLand)
        private val tvOverviewLand = itemView.findViewById<TextView>(R.id.tvOverviewLand)
        private val ivBackdrop = itemView.findViewById<ImageView>(R.id.ivBackdrop)
        fun bind(movie: Movie) {
            if(config.orientation == Configuration.ORIENTATION_PORTRAIT){
                tvTitle.text = movie.title
                tvOverview.text = movie.overview
                Glide.with(context).load(movie.posterImageURL).into(ivPoster)
            }
            else if( config.orientation == Configuration.ORIENTATION_LANDSCAPE){
                tvTitleLand.text = movie.title
                tvOverviewLand.text = movie.overview
                Glide.with(context).load(movie.backdropImageURL).into(ivBackdrop)
            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder posotion $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

}
