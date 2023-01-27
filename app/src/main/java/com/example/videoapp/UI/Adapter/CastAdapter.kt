package com.example.videoapp.UI.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videoapp.Models.cast
import com.example.videoapp.R

class CastAdapter (val cast:ArrayList<cast>,val fragment: Fragment) : RecyclerView.Adapter<CastAdapter.Help>() {


    class Help(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var castImage=itemView.findViewById<ImageView>(R.id.castImage)
        var castName=itemView.findViewById<TextView>(R.id.castName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Help {
        val view=
            LayoutInflater.from(parent.context).inflate(R.layout.cast_layout,parent,false)

        return Help(view)
    }

    override fun onBindViewHolder(holder: Help, position: Int) {
        holder.castName.text=cast.get(position).name
        Glide.with(fragment.requireContext()).load(cast.get(position).image)
            .into(holder.castImage)
    }

    override fun getItemCount(): Int {
        return cast.size
    }
}