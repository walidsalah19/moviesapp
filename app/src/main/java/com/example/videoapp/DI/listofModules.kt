package com.example.videoapp.DI

import RetrofitApi
import com.example.videoapp.MVVM.Details.DetailsViewModule
import com.example.videoapp.MVVM.Details.MovieDetailsRepo
import com.example.videoapp.MVVM.List.MoviesListRepo
import com.example.videoapp.MVVM.List.MoviesViewModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModule: Module = module {
   viewModel{ MoviesViewModule(repo=get())}
    viewModel { DetailsViewModule(repo=get()) }
}
val moviesListRepo:Module= module {
    single { MoviesListRepo(api=get()) }
}
val moviesDetailsRepo:Module= module {
    single { MovieDetailsRepo(api=get()) }
}
val api:Module= module {
    single { RetrofitApi.getRetrofit() }
}
