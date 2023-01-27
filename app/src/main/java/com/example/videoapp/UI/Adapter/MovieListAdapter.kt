package com.example.videoapp.UI.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videoapp.MoviesModel
import com.example.videoapp.R
import com.example.videoapp.UI.ListMoviesDirections

class MovieListAdapter(val fragment:Fragment,val movies: MoviesModel) : RecyclerView.Adapter<MovieListAdapter.Help>() {


    class Help(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var image=itemView.findViewById<ImageView>(R.id.movieImage)
        var name =itemView.findViewById<TextView>(R.id.movieName)
        var date=itemView.findViewById<TextView>(R.id.movieDate)
        var type=itemView.findViewById<TextView>(R.id.movieType)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Help {
       var v=LayoutInflater.from(parent.context).inflate(R.layout.view_movie_layout,parent,false)

        return Help(v)
    }

    override fun onBindViewHolder(holder: Help, position: Int) {
       holder.name.text=movies.data.movies.get(position).title
        holder.date.text=movies.data.movies.get(position).year.toString()
        holder.type.text=movies.data.movies.get(position).genres.get(0)
        Glide.with(fragment.requireContext()).load(movies.data.movies.get(position).coverImage)
            .into(holder.image)
        holder.itemView.setOnClickListener(){

            var synopsis = movies.data.movies.get(position).synopsis
            var b=Bundle()
            b.putInt("id",movies.data.movies.get(position).id)
            b.putString("synopsis",synopsis)
            NavHostFragment.findNavController(fragment).navigate(R.id.movieDetails,b)
        }
    }

    override fun getItemCount(): Int {
        if(movies==null)
            return 0
        else
            return movies.data.movies.size
    }
}