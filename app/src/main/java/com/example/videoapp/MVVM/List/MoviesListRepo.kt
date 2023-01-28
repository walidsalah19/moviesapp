package com.example.videoapp.MVVM.List

import Api
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.videoapp.MoviesModel
import kotlinx.coroutines.*
import notifiyedDataChanged

class MoviesListRepo(private val api: Api) {
    private  var moviesModel= ArrayList<MoviesModel>()
    lateinit var notifyChange: notifiyedDataChanged
    fun initializeModel(mFragment: Fragment)
    {
        notifyChange =mFragment as notifiyedDataChanged
    }
    private  fun getData() {
        GlobalScope.launch (Dispatchers.IO) {
            val response = api.getMovies();
            withContext(Dispatchers.Main)
            {
                moviesModel.add(response)
                notifyChange.dataChanged()
            }
        }
    }
    fun liveData(): MutableLiveData<ArrayList<MoviesModel>>
    {
        moviesModel.clear()
        getData()
        var mMutable= MutableLiveData<ArrayList<MoviesModel>>()
        mMutable.postValue(moviesModel)
        return mMutable
    }





}