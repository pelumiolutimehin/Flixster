package com.example.flixster

import android.app.Activity
import android.app.ActivityOptions
import android.app.ActivityOptions.makeSceneTransitionAnimation
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.util.Pair as UtilPair
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.core.content.ContextCompat.startActivity




private const val TAG = "MovieAdapter"
const val MOVIE_EXTRA = "MOVIE_EXTRA"
class MovieAdapter(private val activity: MainActivity,private val context: Context, private val movies: List<Movie>, private val config: Configuration)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

//    interface OnClickListener{
//        fun onItemClicked(position: Int)
//    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),  View.OnClickListener {
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val ivBackdrop = itemView.findViewById<ImageView>(R.id.ivBackdrop)

        init{
            itemView.setOnClickListener(this)
        }
        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            if(config.orientation == Configuration.ORIENTATION_PORTRAIT){
                Glide.with(context).load(movie.posterImageURL).into(ivPoster)
            }
            else if( config.orientation == Configuration.ORIENTATION_LANDSCAPE){
                Glide.with(context).load(movie.backdropImageURL).into(ivBackdrop)
            }
        }

        override fun onClick(v: View?) {
            val movie = movies[adapterPosition]

            val intent = Intent(context, DetailActivity::class.java)

            intent.putExtra(MOVIE_EXTRA, movie)

//            val (first, second) = UtilPair.create(tvTitle as View?, "profile")
//            val (first1, second1) = Pair.create(vPalette, "palette")
//            val (first2, second2) = Pair.create(tvName as View?, "text")
//
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                activity,
//                (tvTitle as View?)!!, "title",
//                (tvOverview as View?)!!,
//            )
//            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra(MOVIE_EXTRA, movie)
//
            val options = ActivityOptions.makeSceneTransitionAnimation(
                activity,
                UtilPair.create(tvTitle, "title"),
                UtilPair.create(tvOverview, "overview")
            )
            startActivity(context, intent, options.toBundle())

//
////            ActivityOptionsCompat options = ActivityOptionsCompat.
////            makeSceneTransitionAnimation(MovieAdapter, (TextView)tvTitle, "profile");
////            startActivity(intent, options.toBundle())
////            context.startActivity(intent)
//            startActivity(context, intent, options.toBundle())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

}
