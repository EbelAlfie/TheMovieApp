package com.example.themovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.themovieapp.databinding.MovieItemBinding
import com.example.themovieapp.model.MovieModel
import com.example.themovieapp.viewmodel.MovieViewModel
import com.squareup.picasso.Picasso

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: MovieItemBinding
    private var id: Int = -1
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = this.intent.getIntExtra(Utils.ID_KEY, -1)
        initViewModel()
        if (id != -1){
            viewModel.getMovie(id!!)
            setObserver()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
    }

    private fun setObserver() {
        viewModel.getMovieItem().observe(this){
            if (it.id == -1) return@observe
            setView(it)
        }
    }

    private fun setView(it: MovieModel) {
        Picasso.get().load(Utils.BASE_IMAGE_URL + it.image).into(binding.imgMovie)
        binding.tvMovieTitle.text = it.name
        binding.tvYear.text = it.year
        binding.tvRating.text = it.rating.toString()
    }
}