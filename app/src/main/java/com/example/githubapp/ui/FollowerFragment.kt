
package com.example.githubapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.data.response.FollowerResponse
import com.example.githubapp.data.retrofit.ApiConfig
import com.example.githubapp.data.response.FollowingResponse
import com.example.githubapp.databinding.FragmentFollowerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowerFragment : Fragment() {
    private lateinit var binding: FragmentFollowerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        val data = arguments?.getString("username")
        if (data != null) {
            fetchData(data)
        }

        binding.progressBar.visibility = View.VISIBLE

        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvFollower.layoutManager = linearLayoutManager

        return binding.root
    }

    private fun fetchData(query: String) {
        val client = ApiConfig.apiService
        val call: Call<List<FollowerResponse>> = client.getFollowers(query)

        call.enqueue(object : Callback<List<FollowerResponse>> {
            override fun onResponse(
                call: Call<List<FollowerResponse>>,
                response: Response<List<FollowerResponse>>
            ) {
                if (response.isSuccessful) {
                    val user: List<FollowerResponse>? = response.body()
                    Log.d("data", user.toString())
                    if (user != null) {
                        renderData(user)
                    }
                }
            }   

            override fun onFailure(call: Call<List<FollowerResponse>>, t: Throwable) {
                Log.d("error", t.toString())
            }
        })
    }

    fun renderData(data: List<FollowerResponse>) {
        binding.progressBar.visibility = View.INVISIBLE
        val adapter = FollowerAdapter(data)
        binding.rvFollower.adapter = adapter
    }
}

