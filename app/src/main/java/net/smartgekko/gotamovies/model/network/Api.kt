package net.smartgekko.gotamovies.model.network

import net.smartgekko.gotamovies.model.GetMoviesResponse
import net.smartgekko.gotamovies.utils.nytApiKey
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface Api {
    @GET("reviews/all.json")
    fun getMoviesFromApi(
        @Query("offset") page: Int,
        @Query("api-key") apiKey: String
    ): Call<GetMoviesResponse>
}