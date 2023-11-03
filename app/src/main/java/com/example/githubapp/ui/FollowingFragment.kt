
package com.example.githubapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.data.retrofit.ApiConfig
import com.example.githubapp.data.response.FollowingResponse
import com.example.githubapp.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val data = arguments?.getString("username")
        if (data != null) {
            fetchData(data)
        }

        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvFollowing.layoutManager = linearLayoutManager

        return binding.root
    }

    private fun fetchData(query: String) {
        val client = ApiConfig.apiService
        val call: Call<List<FollowingResponse>> = client.getFollowing(query)

        call.enqueue(object : Callback<List<FollowingResponse>> {
            override fun onResponse(
                call: Call<List<FollowingResponse>>,
                response: Response<List<FollowingResponse>>
            ) {
                if (response.isSuccessful) {
                    val user: List<FollowingResponse>? = response.body()
                    Log.d("berhasil", user.toString())
                    if (user != null) {
                        renderData(user)
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowingResponse>>, t: Throwable) {
                Log.d("error", t.toString())
            }
        })
    }

    fun renderData(data: List<FollowingResponse>) {
        binding.progressBar.visibility = View.INVISIBLE
        val adapter = FollowingAdapter(data)
        binding.rvFollowing.adapter = adapter
    }
}

