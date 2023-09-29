package com.example.githubapp.data.viewModel

import androidx.lifecycle.ViewModel
import com.example.githubapp.data.response.DetailResponse

class DetailViewModel: ViewModel() {
    private lateinit var data: DetailResponse
    private var checkData = "empty"

    fun setCheckData(status: String){
        this.checkData = status
    }

    fun setData(data: DetailResponse){
        this.data = data
    }

    fun getData(): DetailResponse{
        return this.data
    }

    fun getStatus(): String{
        return this.checkData
    }
}