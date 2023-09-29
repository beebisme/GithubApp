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
import com.example.githubapp.data.response.FollowingResponse

class FollowingAdapter(private val dataList: List<FollowingResponse>?, ) :
    RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_following, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList?.get(position)
        if (item != null) {
            holder.textViewItem.text = item.login
        }
        if (item != null) {
            Glide.with(holder.itemView)
                .load(item.avatarUrl)
                .into(holder.imgPhoto)
        }
    }

    override fun getItemCount(): Int {
        val data = 0

        if (dataList != null){
            return dataList.size
        } else {
            return data
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewItem: TextView = itemView.findViewById(R.id.tvItemFollowing)
        val imgPhoto: ImageView = itemView.findViewById(R.id.imgPhotoFollowing)
    }
}