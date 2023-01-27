package com.example.videoapp.Models
import com.google.gson.annotations.SerializedName

class MovieDetailsModel(
         @SerializedName("data")
         val data: data) {}

class data(
        @SerializedName("movie")
        val movie:movie) {}

class movie(
        @SerializedName("title")
        val title:String,
        @SerializedName("year")
        val year:Int ,
        @SerializedName("genres")
        val genres:List<String>,
        @SerializedName("medium_cover_image")
        val CoverImage:String,
        @SerializedName("yt_trailer_code")
        val trailer:String,
        @SerializedName("rating")
        val rating:Float,
        @SerializedName("background_image")
        val backgroundImage:String,
        @SerializedName("medium_screenshot_image1")
        val image1:String,
        @SerializedName("medium_screenshot_image2")
        val image2:String,
        @SerializedName("medium_screenshot_image3")
        val image3:String,
        @SerializedName("cast")
        val cast:List<cast>,
        ) {}
class cast(
            @SerializedName("name")
            val name:String,
            @SerializedName("url_small_image")
            val image:String ) {}