package com.example.githubapp.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubapp.data.favorite.Dao
import com.example.githubapp.data.favorite.database
import com.example.githubapp.data.favorite.favUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: Dao?
    private var userDb: database?

    init {
        userDb = database.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<favUser>>?{
        return userDao?.getFavoriteUser()
    }

    fun addToFavorite(username: String, id: Int, avatar_url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = favUser(
                username,
                id,
                avatar_url,
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}