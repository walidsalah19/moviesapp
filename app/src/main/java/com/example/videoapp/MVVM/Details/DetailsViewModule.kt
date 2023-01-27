package com.example.videoapp.MVVM.Details

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.videoapp.Models.MovieDetailsModel

class DetailsViewModule : ViewModel() {

    private var mData= MutableLiveData<ArrayList<MovieDetailsModel>>()
    fun intialViewModel(mFragment: Fragment,id:Int)
    {
        var mmodel= MovieDetailsRepo.initializeModel(mFragment,id)
        mData=mmodel.liveData()
        Log.d("error", "er4")
    }
    fun getData(): MutableLiveData<ArrayList<MovieDetailsModel>> {
        return mData
    }
}