package com.example.githubapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.data.response.SearchResponse
import com.example.githubapp.data.retrofit.ApiConfig
import com.example.githubapp.data.retrofit.ApiService
import com.example.githubapp.data.retrofit.UserResponse
import com.example.githubapp.data.viewModel.SearchViewModel
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