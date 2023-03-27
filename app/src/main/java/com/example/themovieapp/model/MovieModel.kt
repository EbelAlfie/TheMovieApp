package com.example.themovieapp.model

import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.themovieapp.R
import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("backdrop_path")
    val image: String,
    @SerializedName("vote_average")
    val rating: Float,
    @SerializedName("release_date")
    val year: String,
    @SerializedName("original_title")
    val name: String,
    @SerializedName("overview")
    val desc: String,
) {
    fun setColor(): Int {
        return when (rating) {
            in 0.0..4.9 -> R.color.red
            in 5.0..6.9 -> R.color.yellow
            in 7.0..10.0 -> R.color.green
            else -> R.color.red
        }
    }
}