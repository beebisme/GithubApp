package com.example.githubapp.data.favorite

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteUserList(
    val username: String,
    val avatarUrl: String,
    val userId: Int,
): Parcelable
