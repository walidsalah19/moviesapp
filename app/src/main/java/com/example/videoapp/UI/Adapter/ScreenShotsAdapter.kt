package com.example.videoapp.UI.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videoapp.R

class ScreenShotsAdapter(val images:ArrayList<String>,val fragment:Fragment) : RecyclerView.Adapter<ScreenShotsAdapter.Help>() {


    class Help(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var image=itemView.findViewById<ImageView>(R.id.screenShot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Help {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.screenshots_layout,parent,false)

        return Help(view)
    }

    override fun onBindViewHolder(holder: Help, position: Int) {
        Glide.with(fragment.requireContext()).load(images.get(position))
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}