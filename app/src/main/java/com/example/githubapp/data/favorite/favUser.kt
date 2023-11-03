package com.example.githubapp.data.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class favUser(
    val login: String,
    @PrimaryKey
    val id:Int,
    val avatar_url:String
): Serializable
