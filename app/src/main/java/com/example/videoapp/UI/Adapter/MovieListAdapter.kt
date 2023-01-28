package com.example.videoapp.UI.Adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videoapp.MoviesModel
import com.example.videoapp.R
import com.example.videoapp.UI.ListMoviesDirections

class MovieListAdapter(val fragment:Fragment,val movies: MoviesModel) : RecyclerView.Adapter<MovieListAdapter.Help>() {
    var color:Int=0
    class Help(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var image=itemView.findViewById<ImageView>(R.id.movieImage)
        var name =itemView.findViewById<TextView>(R.id.movieName)
        var date=itemView.findViewById<TextView>(R.id.movieDate)
        var type=itemView.findViewById<TextView>(R.id.movieType)
        var background=itemView.findViewById<ConstraintLayout>(R.id.background)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Help {
       var v=LayoutInflater.from(parent.context).inflate(R.layout.view_movie_layout,parent,false)

        return Help(v)
    }

    override fun onBindViewHolder(holder: Help, position: Int) {
        holder.name.text=movies.data.movies.get(position).title
        holder.date.text=movies.data.movies.get(position).year.toString()
        holder.type.text=movies.data.movies.get(position).genres.get(0)
        changeColor(holder)
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

    @SuppressLint("ResourceAsColor")
    private fun changeColor(holder: Help) {
        when (color) {
            0 -> holder.background.setBackgroundColor(fragment.resources.getColor(R.color.color1))
            1 -> holder.background.setBackgroundColor(fragment.resources.getColor(R.color.color2))
            2 -> holder.background.setBackgroundColor(fragment.resources.getColor(R.color.color3))
            3 -> holder.background.setBackgroundColor(fragment.resources.getColor(R.color.color4))
            4 -> holder.background.setBackgroundColor(fragment.resources.getColor(R.color.color5))
            5 -> holder.background.setBackgroundColor(fragment.resources.getColor(R.color.color6))
        }

         color+=1
        if (color>5)
            color=0
    }
    override fun getItemCount(): Int {
        if(movies==null)
            return 0
        else
            return movies.data.movies.size
    }
}