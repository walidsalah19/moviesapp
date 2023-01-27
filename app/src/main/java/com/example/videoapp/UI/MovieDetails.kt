package com.example.videoapp.UI

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videoapp.MVVM.Details.DetailsViewModule
import com.example.videoapp.Models.MovieDetailsModel
import com.example.videoapp.Models.cast
import com.example.videoapp.R
import com.example.videoapp.UI.Adapter.CastAdapter
import com.example.videoapp.UI.Adapter.ScreenShotsAdapter
import com.example.videoapp.databinding.FragmentMovieDetailsBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import notifiyedDataChanged

class MovieDetails : Fragment() , notifiyedDataChanged{
    private lateinit var  mBinding:FragmentMovieDetailsBinding;
    private lateinit var viewModel:DetailsViewModule
    private  var images=ArrayList<String>()
    private  var casts=ArrayList<cast>()

    private lateinit var imageAdapter:ScreenShotsAdapter
    private lateinit var castAdapter:CastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding= FragmentMovieDetailsBinding.inflate(inflater,container,false)
        val id= requireArguments().getInt("id")
        val synopsis=requireArguments().getString("synopsis")

        initializeRecyclerView()

        goBack()
        viewModel= ViewModelProvider(this).get(DetailsViewModule::class.java)
        viewModel.intialViewModel(this,id)
        mBinding.synopsisText.text=synopsis


        return mBinding.root
    }
    private fun initializeRecyclerView()
    {
        imageAdapter=ScreenShotsAdapter(images,this)
        mBinding.screenShotsRecycler.apply {
            layoutManager=LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,true)
        }
        castAdapter= CastAdapter(casts,this)
        mBinding.CastRecycler.apply {
            layoutManager=LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,true)
        }
    }
    private fun goBack()
    {
        mBinding.goBack.setOnClickListener()
        {
            NavHostFragment.findNavController(this).navigate(R.id.ListMovies)
        }
    }


    override fun dataChanged() {
        viewModel.getData().observe(viewLifecycleOwner) {
            Log.e("this","adsdasdasdasdsadasdas")
            addTOView(it.get(0))
            displayTrailer(it.get(0).data.movie.trailer)
            displayScreenShots(it.get(0).data.movie.image1,it.get(0).data.movie.image2,it.get(0).data.movie.image3)
            displayCast(it.get(0).data.movie.cast)
        }
    }

    private fun displayCast(cast: List<cast>) {
        if (cast==null) {
            mBinding.Cast.setVisibility(View.INVISIBLE)
        }
        else {
            casts.addAll(cast)
            mBinding.CastRecycler.adapter = castAdapter
            castAdapter.notifyDataSetChanged()
        }
    }

    private fun displayScreenShots(image1: String, image2: String, image3: String) {
        images.add(image1)
        images.add(image2)
        images.add(image3)
        Log.e("image",image1)
        mBinding.screenShotsRecycler.adapter=imageAdapter
        imageAdapter.notifyDataSetChanged()

    }


    private fun displayTrailer(trailer: String) {

     mBinding.youtubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener()
        {

            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(
                    trailer,
                    0.0F
                )
                var defaultPlayerUiController: DefaultPlayerUiController =
                    DefaultPlayerUiController(mBinding.youtubePlayerView, youTubePlayer)
                mBinding.youtubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                defaultPlayerUiController.showDuration(true)
                defaultPlayerUiController.showDuration(false)

            }
            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                super.onError(youTubePlayer, error)
                Log.d("this",youTubePlayer.toString())
            }
        })
    }
    @SuppressLint("SetTextI18n")
    private fun addTOView(details:MovieDetailsModel)
    {
        mBinding.date.text=details.data.movie.year.toString()
        mBinding.rate.text=details.data.movie.rating.toString()+"/10"
        Glide.with(requireContext()).load(details.data.movie.CoverImage)
            .into(mBinding.coverImage)
        var genres=""
        for (i in details.data.movie.genres)
        {
            genres+=i+"\t"
        }
        mBinding.type.text=genres
    }
}