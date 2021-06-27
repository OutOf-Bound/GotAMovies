package net.smartgekko.gotamovies.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import net.smartgekko.gotamovies.R
import net.smartgekko.gotamovies.model.Movie

class MoviesAdapter(private var movies: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_list_item_vertical, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])

    }

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        private val title: TextView = itemView.findViewById(R.id.item_movie_title)
        private val release_date: TextView = itemView.findViewById(R.id.item_movie_release_date)
        private val overwiew: TextView = itemView.findViewById(R.id.item_movie_overview)

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load(movie.multimedia.posterPath)
                .transform(CenterCrop())
                .into(poster)

            title.text = movie.title
            release_date.text = movie.releaseDate


            val string_limit = 250
            val overview_string: String = movie.overview

            if (overview_string.length < string_limit) {
                overwiew.text = overview_string
            } else {
                overwiew.text = overview_string.substring(0, string_limit) + "..."
            }
        }
    }
}