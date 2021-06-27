package net.smartgekko.gotamovies.model

import com.google.gson.annotations.SerializedName

data class GetMoviesResponse
    (
    @SerializedName("results") val movies: List<Movie>
)