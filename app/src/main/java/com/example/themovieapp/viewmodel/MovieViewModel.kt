package com.example.themovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themovieapp.model.MovieModel
import com.example.themovieapp.repository.Repository

class MovieViewModel: ViewModel() {
    private val repo: Repository = Repository()
    private var _movieItem = MutableLiveData<MovieModel>()
    fun getMovieItem() : LiveData<MovieModel> = _movieItem

    fun getMovie(id: Int) {
        _movieItem = repo.getMovieItem(id) as MutableLiveData<MovieModel>
    }
}