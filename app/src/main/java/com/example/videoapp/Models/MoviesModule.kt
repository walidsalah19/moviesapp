package com.example.videoapp

import com.google.gson.annotations.SerializedName

class MoviesModel(
    @SerializedName("data")
    val data: data) {

}
class data(
    @SerializedName("movies")
    val movies:List<movies>)
{

}
class movies(
    @SerializedName("id")
    val id:Int,
    @SerializedName("title")
    val title:String,
    @SerializedName("year")
    val year:Int ,
    @SerializedName("genres")
    val genres:List<String>,
    @SerializedName("medium_cover_image")
    val coverImage:String,
    @SerializedName("synopsis")
    val synopsis:String)
{

}