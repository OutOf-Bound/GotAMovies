package net.smartgekko.gotamovies.views.activities

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.smartgekko.gotamovies.R
import net.smartgekko.gotamovies.model.Movie
import net.smartgekko.gotamovies.viewmodels.AppState
import net.smartgekko.gotamovies.viewmodels.HomeViewModel
import net.smartgekko.gotamovies.views.adapters.MoviesAdapter

private lateinit var viewModel: HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var moviesList: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private var fullMovieList: ArrayList<Movie> = ArrayList()
    private var currentOffset:Int=0;

    companion object {
        fun newInstance() = HomeFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.home_fragment, container, false)
        val activity = activity as Context
        moviesList = view.findViewById(R.id.movies_list)
        moviesList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        moviesList.addOnScrollListener( object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getMovieList(currentOffset)
                }
            }
        });
        moviesAdapter = MoviesAdapter(listOf())
        moviesList.adapter = moviesAdapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        lifecycle.addObserver(viewModel)
        viewModel.getMovieList(currentOffset)
    }

    private fun renderData(appState: AppState) {
        val loadingLayout: FrameLayout? = view?.findViewById(R.id.loadingLayout)

        when (appState) {
            is AppState.Success -> {
                val movieData = appState.movieData
                loadingLayout?.visibility = View.GONE
                setData(movieData)
            }
            is AppState.Loading -> {
                loadingLayout?.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout?.visibility = View.GONE
                Toast.makeText(context, "No more movies available", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setData(movieData: List<Movie>) {
        var tempList: ArrayList<Movie> = ArrayList<Movie>()
            fullMovieList.addAll(movieData);
            currentOffset+=20
            moviesAdapter.updateMovies(fullMovieList)
    }
}