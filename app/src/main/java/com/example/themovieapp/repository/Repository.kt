package com.example.themovieapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.themovieapp.Utils
import com.example.themovieapp.model.MovieModel
import com.example.themovieapp.model.QueryModel
import com.example.themovieapp.model.services.RetrofitObj
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class Repository {
    private val movieList = MutableLiveData<QueryModel>()
    private val movieItem = MutableLiveData<MovieModel>()


    fun getMovieItem(id: Int): LiveData<MovieModel> {
        movieItem.value = MovieModel(id = -1, name = "", image = "", rating = 0.0f, year = "",  desc = "")
        val response = RetrofitObj.retrofitInstance.getSpesificMovie(id, RetrofitObj.API_KEY)
        response.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<MovieModel>{
                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {}

                override fun onComplete() {}

                override fun onNext(t: MovieModel) {
                    movieItem.postValue(t)
                }

            })
        return movieItem
    }

    fun getResponseType(mode: Int, page: Int): Observable<QueryModel> {
        return when (mode) {
            Utils.GET_POPULAR -> RetrofitObj.retrofitInstance.getAllPopularMovies(RetrofitObj.API_KEY, page)
            Utils.GET_RATED -> RetrofitObj.retrofitInstance.getTopRatedMovies(RetrofitObj.API_KEY, page)
            else -> RetrofitObj.retrofitInstance.getAllPopularMovies(RetrofitObj.API_KEY, page)
        }
    }
}