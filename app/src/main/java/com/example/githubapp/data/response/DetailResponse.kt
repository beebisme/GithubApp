package com.example.githubapp.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailResponse(
    val userId: Int,
    val username: String,
    val name: String,
    val avatarUrl: String,
    val following: Int,
    val follower: Int
): Parcelable