package com.example.githubapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.data.favorite.Dao
import com.example.githubapp.data.favorite.FavoriteUserList
import com.example.githubapp.data.favorite.database
import com.example.githubapp.data.favorite.favUser
import com.example.githubapp.data.viewModel.FavoriteViewModel
import com.example.githubapp.data.viewModel.AppModeViewModel
import com.example.githubapp.data.viewModel.AppModeViewModelFactory
import com.example.githubapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private var userDao : Dao? = null
    private var userDb : database? = null
    private lateinit var favVM: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favVM = ViewModelProvider(this)[FavoriteViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        userDb = database.getDatabase(application)
        userDao = userDb?.favoriteUserDao()

        val adapter = FavAdapter()
        binding.recyclerView.adapter = adapter

        favVM.getFavoriteUser()?.observe(this){
            val list = userList(it)
            adapter.setDataList(list)
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, AppModeViewModelFactory(pref))[AppModeViewModel::class.java]

        var isChecked = true
        settingViewModel.getThemeSettings().observe(this){isDarkModeActive : Boolean ->
            isChecked = if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                false
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                true
            }
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    val intent1 = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent1)
                    true
                }
                R.id.menu2 -> {
                    settingViewModel.saveThemeSetting(isChecked)
                    true
                }

                else -> false
            }
        }
    }

    private fun userList(favuser: List<favUser>?): ArrayList<FavoriteUserList> {
        val favUserList = ArrayList<FavoriteUserList>()

        if (favuser != null) {
            for (user in favuser){
                val users = FavoriteUserList(
                    user.login,
                    user.avatar_url,
                    user.id
                )
                favUserList.add(users)
            }
        }

        return favUserList
    }
}