package com.example.videoapp.MVVM.Details

import Api
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.videoapp.MVVM.List.MoviesListRepo
import com.example.videoapp.Models.MovieDetailsModel
import com.example.videoapp.MoviesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import notifiyedDataChanged
import kotlin.properties.Delegates

class MovieDetailsRepo {
    private  lateinit var api: Api
    private val BASE_URL="https://yts.mx/api/v2/"
    private  var moviesDetails= ArrayList<MovieDetailsModel>()

    companion object
    {
        private lateinit var notifyChange: notifiyedDataChanged
        private var id by Delegates.notNull<Int>()
        fun initializeModel(mFragment: Fragment,id:Int): MovieDetailsRepo
        {
            this.id=id
            notifyChange =mFragment as notifiyedDataChanged
            return MovieDetailsRepo()

        }
    }
    private  fun getData() {
        api = Retrofit.getRetrofit(BASE_URL).create(Api::class.java)
        GlobalScope.launch (Dispatchers.IO) {
            val response = api.getMovieDetails(id);
            withContext(Dispatchers.Main)
            {
                moviesDetails.add(response)
                notifyChange.dataChanged()
            }
        }
    }

    fun liveData(): MutableLiveData<ArrayList<MovieDetailsModel>>
    {
        getData()
        var mMutable= MutableLiveData<ArrayList<MovieDetailsModel>>()
        if (moviesDetails!=null) {
            mMutable.postValue(moviesDetails)
        }
        return mMutable
    }
}