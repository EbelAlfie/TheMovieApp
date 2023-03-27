package com.example.themovieapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovieapp.databinding.MovieItemBinding
import com.example.themovieapp.model.MovieModel
import com.squareup.picasso.Picasso

class MovieAdapter(var movieList: List<MovieModel>, val listener: SetOnItemClicked): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    class MovieViewHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun updateList(newMovie: List<MovieModel>) {
        movieList = newMovie
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val allViews = holder.binding
        val data = movieList[position]

        Picasso.get()
            .load(Utils.BASE_IMAGE_URL + data.image)
            .resize(300, 300)
            .into(allViews.imgMovie)

        allViews.tvMovieTitle.text = data.name
        allViews.tvRating.text = data.rating.toString()
        allViews.tvYear.text = data.year

        //allViews.tvRating.setBackgroundColor(data.setColor())

        allViews.root.setOnClickListener {
            listener.onItemClicked(position)
        }
    }

    interface SetOnItemClicked {
        fun onItemClicked(position: Int)
    }

    fun getId(position: Int): Int = movieList[position].id

}