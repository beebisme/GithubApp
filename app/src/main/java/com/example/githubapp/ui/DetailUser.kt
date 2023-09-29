package com.example.githubapp.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.data.response.SearchResponse
import com.example.githubapp.data.response.DetailResponse
import com.example.githubapp.data.viewModel.DetailViewModel
import com.example.githubapp.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailUser : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailVM: DetailViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailVM = ViewModelProvider(this)[DetailViewModel::class.java]

        binding.progressBar.visibility = View.VISIBLE

        val dataUser = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("key_detail", SearchResponse::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("key_detail")
        }

        if (detailVM.getStatus() == "empty"){
            if (dataUser != null) {
                getUserDetail(dataUser.login.toString())

            }
        } else {
            renderData(detailVM.getData())
        }
    }

    private fun getUserDetail(username: String){
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username"

        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                // Jika koneksi berhasil
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val data = parseMyDataFromResponse(responseObject)
                    detailVM.setCheckData("Exist")
                    detailVM.setData(data)
                    renderData(data)
                } catch (e: Exception) {
                    Toast.makeText(this@DetailUser, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@DetailUser, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun parseMyDataFromResponse(response: JSONObject): DetailResponse {
        val username = response.getString("login")
        val name = response.getString("name")
        val avatar = response.getString("avatar_url")
        val following = response.getInt("following")
        val followers = response.getInt("followers")
        return DetailResponse(username, name, avatar, following, followers )
    }

    private fun renderData(data: DetailResponse){
        val nama: String = if (data.name == "null"){
            "name not found"
        } else {
            data.name
        }
        val username = "@${data.username}"
        val follow = "following: ${data.following} \t followers: ${data.follower}"
        binding.tvName.text = nama
        binding.tvUsername.text = username
        binding.tvFollow.text = follow

        Glide.with(binding.profilePhoto)
            .load(data.avatarUrl)
            .into(binding.profilePhoto)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, data.username)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        binding.progressBar.visibility = View.INVISIBLE
    }

}