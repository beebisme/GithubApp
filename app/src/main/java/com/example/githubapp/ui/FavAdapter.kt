package com.example.githubapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.data.favorite.FavoriteUserList

class FavAdapter :
    RecyclerView.Adapter<FavAdapter.ViewHolder>() {

    private val dataList = ArrayList<FavoriteUserList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.textViewItem.text = item.username
        Glide.with(holder.itemView)
                .load(item.avatarUrl)
                .into(holder.imgPhoto)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailUser::class.java)
            intentDetail.putExtra("username_detail", dataList[holder.adapterPosition].username)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewItem: TextView = itemView.findViewById(R.id.tvItem)
        val imgPhoto: ImageView = itemView.findViewById(R.id.imgPhoto)
    }

    fun setDataList(users: ArrayList<FavoriteUserList>) {
        dataList.clear()
        dataList.addAll(users)
        notifyDataSetChanged()
    }
}