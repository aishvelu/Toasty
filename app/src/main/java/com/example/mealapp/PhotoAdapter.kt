package com.example.mealapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealapp.screens.RecipeInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_layout.view.*

class PhotoAdapter(var dataList: List<RecipeInfo>, var context: Context) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    internal fun setDataList(dataList: List<RecipeInfo>) {
        this.dataList = dataList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView

        init {
            image = itemView.findViewById(R.id.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.grid_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = dataList[position]
        Picasso.get().load(data.thumbnail_url).into(holder.image)
    }

    override fun getItemCount() = dataList.size
}