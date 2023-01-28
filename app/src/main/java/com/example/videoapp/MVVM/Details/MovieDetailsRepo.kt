package com.example.videoapp.MVVM.Details

import Api
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.videoapp.Models.MovieDetailsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import notifiyedDataChanged
import kotlin.properties.Delegates

class MovieDetailsRepo (private val api:Api){
    private  var moviesDetails= ArrayList<MovieDetailsModel>()
    private lateinit var notifyChange: notifiyedDataChanged
    private var id :Int?=null
    fun initializeModel(mFragment: Fragment,id:Int)
        {
            this.id=id
            notifyChange =mFragment as notifiyedDataChanged
        }
    private  fun getData() {
        GlobalScope.launch (Dispatchers.IO) {
            val response = api.getMovieDetails(id);
            Log.e("id2",id.toString())
            withContext(Dispatchers.Main)
            {
                moviesDetails.add(response)
                notifyChange.dataChanged()
            }
        }
    }

    fun liveData(): MutableLiveData<ArrayList<MovieDetailsModel>>
    {
        moviesDetails.clear()
        getData()
        var mMutable= MutableLiveData<ArrayList<MovieDetailsModel>>()
        mMutable.postValue(moviesDetails)
        return mMutable
    }
}