package com.example.videoapp.MVVM.List

import Api
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.videoapp.MoviesModel
import kotlinx.coroutines.*
import notifiyedDataChanged

class MoviesListRepo {
    private  lateinit var api: Api
    private val BASE_URL="https://yts.mx/api/v2/"
    private  var moviesModel= ArrayList<MoviesModel>()
    lateinit var notifyChange: notifiyedDataChanged
    fun initializeModel(mFragment: Fragment): MoviesListRepo
    {
        notifyChange =mFragment as notifiyedDataChanged
        return MoviesListRepo()
    }
    private  fun getData() {
        api = Retrofit.getRetrofit(BASE_URL).create(Api::class.java)
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
        getData()
        var mMutable= MutableLiveData<ArrayList<MoviesModel>>()
        if (moviesModel!=null) {
            mMutable.postValue(moviesModel)
        }
        return mMutable
    }





}