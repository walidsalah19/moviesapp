package com.example.videoapp.UI

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videoapp.MVVM.List.MoviesViewModule
import com.example.videoapp.MoviesModel
import com.example.videoapp.R
import com.example.videoapp.UI.Adapter.MovieListAdapter
import com.example.videoapp.databinding.FragmentListMoviesBinding
import notifiyedDataChanged
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMovies : Fragment() , notifiyedDataChanged {
    private lateinit var movieListBinding: FragmentListMoviesBinding
    private val mMoviesModel: MoviesViewModule by sharedViewModel()
    private lateinit var listMovies: MoviesModel
    private val adapter: MovieListAdapter by lazy {
       MovieListAdapter(this,listMovies)
    }
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
        mMoviesModel.intialViewModel(this)
        statusBarColor()
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
            listMovies=it.get(0)
            movieListBinding.listMovies.adapter=adapter
            adapter.notifyDataSetChanged()
        }
    }
    private fun statusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.purple_700)

        }
    }
}