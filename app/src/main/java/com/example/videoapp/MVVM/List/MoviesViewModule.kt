package com.example.videoapp.MVVM.List

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.videoapp.MoviesModel

class MoviesViewModule(private val repo: MoviesListRepo ) :ViewModel() {

    private var mData= MutableLiveData<ArrayList<MoviesModel>>()
    fun intialViewModel(mFragment: Fragment)
    {
        repo.initializeModel(mFragment)
        mData=repo.liveData()
        Log.d("error", "er4")
    }
    fun getData(): MutableLiveData<ArrayList<MoviesModel>> {
        return mData
    }
}