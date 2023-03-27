package com.example.themovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themovieapp.Utils
import com.example.themovieapp.model.QueryModel
import com.example.themovieapp.repository.Repository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel(){
    private val repo = Repository()
    private var _popularMovieData= MutableLiveData<QueryModel>()
    private var _topRatedMovieData = MutableLiveData<QueryModel>()

    private lateinit var response: Observable<QueryModel>

    fun getPopularMovieData(): LiveData<QueryModel> = _popularMovieData

    fun getTopRatedMovieData(): LiveData<QueryModel> = _topRatedMovieData

    init {
        getAllPopularMovies(20)
        getTopRatedMovie(20)
    }

    private fun getAllPopularMovies(page: Int) {
        response = repo.getResponseType(Utils.GET_POPULAR, page)
        getMovies(Utils.GET_POPULAR)
    }

    private fun getTopRatedMovie(page: Int) {
        response = repo.getResponseType(Utils.GET_RATED, page)
        getMovies(Utils.GET_RATED)
    }


    fun getMovies(mode: Int) {
        if (mode == Utils.GET_POPULAR) {
            _popularMovieData.value = QueryModel(
                loadingStatus = true, errorMsg = "",
                result = mutableListOf(), page = 0
            )
        }else {
            _topRatedMovieData.value = QueryModel(
                loadingStatus = true, errorMsg = "",
                result = mutableListOf(), page = 0
            )
        }
        response.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<QueryModel> {
                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {
                    if (mode == Utils.GET_POPULAR) {
                        _popularMovieData.value = QueryModel(
                            loadingStatus = false, errorMsg = e.message.toString(),
                            result = mutableListOf(), page = 0
                        )
                    }else {
                        _topRatedMovieData.value = QueryModel(
                            loadingStatus = false, errorMsg = e.message.toString(),
                            result = mutableListOf(), page = 0
                        )
                    }
                }

                override fun onComplete() {}

                override fun onNext(t: QueryModel) {
                    if (mode == Utils.GET_POPULAR) {
                        _popularMovieData.postValue(QueryModel(
                            loadingStatus = false,
                            errorMsg = "",
                            result = t.result,
                            page = t.page
                        ))
                    }else {
                        _topRatedMovieData.postValue(QueryModel(
                            loadingStatus = false,
                            errorMsg = "",
                            result = t.result,
                            page = t.page
                        ))
                    }
                }
            })
    }
}