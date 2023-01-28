

import com.example.videoapp.Models.MovieDetailsModel
import com.example.videoapp.MoviesModel
import com.example.videoapp.UI.ListMoviesDirections
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
  @GET("list_movies.json")
   suspend fun getMovies(): MoviesModel


  @GET("movie_details.json?&with_images=true&with_cast=true")
  suspend fun getMovieDetails(
      @Query("movie_id") id:Int?
  ):MovieDetailsModel
}