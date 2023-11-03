package com.example.githubapp.data.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {
    @Insert
    suspend fun addToFavorite(favoriteUser: favUser)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): LiveData<List<favUser>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun checkUser (id:Int) : Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun removeFromFavorite(id: Int):Int
}