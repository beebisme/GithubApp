package com.example.githubapp.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.data.response.FollowerResponse

class FollowerAdapter(private val dataList: List<FollowerResponse>) :
    RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_follower, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.textViewItem.text = item.login
        Glide.with(holder.itemView)
            .load(item.avatarUrl)
            .into(holder.imgPhoto)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewItem: TextView = itemView.findViewById(R.id.tvItemFollower)
        val imgPhoto: ImageView = itemView.findViewById(R.id.imgPhotoFollower)
    }
}