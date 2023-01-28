package com.example.videoapp.UI

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.min


class MovieDetails : Fragment() , notifiyedDataChanged{
    private lateinit var  mBinding:FragmentMovieDetailsBinding;
    private  val viewModel:DetailsViewModule by sharedViewModel()
    private  var images=ArrayList<String>()
    private  var casts=ArrayList<cast>()

    private val imageAdapter:ScreenShotsAdapter by lazy {
        ScreenShotsAdapter(images,this)
    }
    private val castAdapter:CastAdapter  by lazy {
         CastAdapter(casts,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding= FragmentMovieDetailsBinding.inflate(inflater,container,false)
        var id= requireArguments().getInt("id")
        Log.e("id",id.toString())
        var synopsis=requireArguments().getString("synopsis")

        initializeRecyclerView()
        viewModel.intialViewModel(this,id)
        mBinding.synopsisText.text=synopsis

        goBack()
        changeToolbar()
        statusBarColor()
        return mBinding.root
    }
    private fun initializeRecyclerView()
    {
        mBinding.screenShotsRecycler.apply {
            layoutManager=LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,true)
        }
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
            setBackground(it.get(0).data.movie.backgroundImage)
            setToolbarTitle(it.get(0).data.movie.title)
        }
    }
    private fun setToolbarTitle(title:String)
    {
        mBinding.title.text=title
    }
    private fun setBackground(image:String)
    {
        Glide.with(requireContext()).load(image)
            .into( mBinding.background)

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
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun changeToolbar()
    {
        mBinding.toolbar.visibility=View.INVISIBLE
        mBinding.title.visibility=View.INVISIBLE

        mBinding.nestedScrollview.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if(scrollY==0)
            {
                mBinding.toolbar.visibility=View.INVISIBLE
                mBinding.title.visibility=View.INVISIBLE
            }
            else {
                mBinding.toolbar.visibility = View.VISIBLE
                mBinding.title.visibility=View.VISIBLE
                 val color = changeAppBarAlpha(
                     ContextCompat.getColor(requireContext(), R.color.color1),
                     (min(255, scrollY).toFloat() / 255.0f).toDouble()
                 )
                mBinding.toolbar.setBackgroundColor(color)
            }
        }
    }
   private fun changeAppBarAlpha(color: Int, fraction: Double): Int {
        val red: Int = Color.red(color)
        val green: Int = Color.green(color)
        val blue: Int = Color.blue(color)
        val alpha: Int = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }
    private fun statusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.color1)
        }
    }
}