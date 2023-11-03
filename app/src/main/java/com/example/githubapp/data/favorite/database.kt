package com.example.githubapp.data.favorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [favUser::class],
    version = 1
)
abstract class database: RoomDatabase() {
    companion object{
        var INSTANCE : database? = null

        fun getDatabase(context: Context): database?{
            if (INSTANCE==null){
                synchronized(database::class){}
                INSTANCE = Room.databaseBuilder(context.applicationContext, database::class.java, "user_database").build()
            }
            return INSTANCE
        }
    }
    abstract fun favoriteUserDao(): Dao
}