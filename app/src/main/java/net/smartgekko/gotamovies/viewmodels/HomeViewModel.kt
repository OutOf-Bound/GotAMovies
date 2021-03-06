package net.smartgekko.gotamovies.viewmodels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.smartgekko.gotamovies.model.Movie
import net.smartgekko.gotamovies.repository.MainRepository

class HomeViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()) :
    ViewModel(), LifecycleObserver {
    private var setOffset: Int = 0

    fun getLiveData() = liveDataToObserve

    fun getMovieList(offset: Int) {
        setOffset = offset
        getDataFromLocalSource()
    }

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {

            fun onMoviesFetched(movies: List<Movie>) {
                liveDataToObserve.postValue(AppState.Success(movies))
            }

            fun onError(throwable: Throwable) {
                liveDataToObserve.postValue(AppState.Error(throwable))
            }

            MainRepository.getMovies(
                setOffset,
                onSuccess = ::onMoviesFetched,
                onError = ::onError
            )
        }.start()
    }
}