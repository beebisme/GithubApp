package com.example.githubapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.data.response.SearchResponse
import com.example.githubapp.data.retrofit.ApiConfig
import com.example.githubapp.data.retrofit.UserResponse
import com.example.githubapp.data.viewModel.SearchViewModel
import com.example.githubapp.data.viewModel.AppModeViewModel
import com.example.githubapp.data.viewModel.AppModeViewModelFactory
import com.example.githubapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    getData(searchView.text.toString())
                    searchViewModel.setCheckData("exist")
                    false
                }

        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.progressBar.visibility = View.INVISIBLE

        if (searchViewModel.getStatus() != "empty"){
            renderData(searchViewModel.getData())
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }



    private fun getData(query: String){
        binding.progressBar.visibility = View.VISIBLE

        val client = ApiConfig.apiService
        val call: Call<UserResponse> = client.getUsers(query)

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    binding.progressBar.visibility = View.INVISIBLE
                    val responseBody = response.body()
                    val user: List<SearchResponse>? = responseBody?.items
                    if (user != null) {
                        searchViewModel.setData(user)
                        renderData(user)
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }
    private fun renderData(users: List<SearchResponse>){
        val adapter = ListUserAdapter(users)
        binding.recyclerView.adapter = adapter
    }
}