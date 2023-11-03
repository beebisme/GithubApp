package com.example.githubapp.data.viewModel

import androidx.lifecycle.ViewModel
import com.example.githubapp.data.response.SearchResponse
import com.example.githubapp.data.response.Users

class SearchViewModel() : ViewModel() {
    private lateinit var data: List<SearchResponse>
    private var checkData = "empty"

    fun setCheckData(status: String){
        this.checkData = status
    }

    fun setData(data: List<SearchResponse>){
        this.data = data
    }

    fun getData(): List<SearchResponse>{
        return this.data
    }

    fun getStatus(): String{
        return this.checkData
    }

}