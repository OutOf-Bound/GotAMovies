package net.smartgekko.gotamovies.repository

import net.smartgekko.gotamovies.model.GetMoviesResponse
import net.smartgekko.gotamovies.model.Movie
import net.smartgekko.gotamovies.model.network.Api
import net.smartgekko.gotamovies.utils.API_BASE_URL
import net.smartgekko.gotamovies.utils.HTTP_AGENT
import net.smartgekko.gotamovies.utils.nytApiKey
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class UserAgentInterceptor(private val userAgent: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originRequest: Request = chain.request()
        val requestWithUserAgent: Request = originRequest.newBuilder()
            .header("User-Agent", userAgent)
            .build()
        return chain.proceed(requestWithUserAgent)
    }
}

object MainRepository {
    private val api: Api
    private var okHttp: OkHttpClient

    init {
        val uA = HTTP_AGENT
        okHttp = OkHttpClient.Builder().addInterceptor(UserAgentInterceptor(uA)).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()
        api = retrofit.create(Api::class.java)
    }

    fun getMovies(
        offset: Int,
        onSuccess: (moviesList: List<Movie>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        api.getMoviesFromApi(offset,nytApiKey)
            .enqueue(object : Callback<GetMoviesResponse> {

                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {

                            onSuccess.invoke(responseBody.movies)
                        } else {
                            val t = Throwable("Empty set")
                            onError.invoke(t)
                        }
                    } else {
                        val t = Throwable("Request failed")
                        onError.invoke(t)
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke(t)
                }
            })
    }
}