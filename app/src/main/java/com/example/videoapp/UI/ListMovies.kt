package com.example.videoapp.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videoapp.MVVM.List.MoviesViewModule
import com.example.videoapp.UI.Adapter.MovieListAdapter
import com.example.videoapp.databinding.FragmentListMoviesBinding
import notifiyedDataChanged

class ListMovies : Fragment() , notifiyedDataChanged {
    private lateinit var movieListBinding: FragmentListMoviesBinding
    private lateinit var mMoviesModel: MoviesViewModule
    private lateinit var adapter: MovieListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        movieListBinding=FragmentListMoviesBinding.inflate(inflater,container,false)
        recyclerViewComponent()
        mMoviesModel= ViewModelProvider(this).get(MoviesViewModule::class.java)
        mMoviesModel.intialViewModel(this)

        return movieListBinding.root
    }
    private fun recyclerViewComponent()
    {
        movieListBinding.listMovies.apply {
            layoutManager= GridLayoutManager(requireContext(),2)

        }
    }
    override fun dataChanged() {
        mMoviesModel.getData().observe(viewLifecycleOwner) {
            Log.e("this","adsdasdasdasdsadasdas")
            adapter = MovieListAdapter(this,it.get(0))
            movieListBinding.listMovies.adapter=adapter
            adapter.notifyDataSetChanged()
        }
    }
}