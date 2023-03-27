package com.example.themovieapp.model.services

import com.example.themovieapp.model.MovieModel
import com.example.themovieapp.model.QueryModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getAllPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Observable<QueryModel>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") key: String,
        @Query("page") page: Int
    ) : Observable<QueryModel>

    @GET("Movies")
    fun getSpesificMovie(@Query("") id: Int, @Query("api_key") key: String): Observable<MovieModel>

    @GET("movie/latest")
    fun getLatestMovie(@Query("api_key") key: String): Observable<QueryModel>
}