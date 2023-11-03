package com.example.githubapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowingFragment()
                val bundle = Bundle()
                bundle.putString("username", username)
                fragment.arguments = bundle
            }
            1 -> {
                fragment = FollowerFragment()
                val bundle = Bundle()
                bundle.putString("username", username)
                fragment.arguments = bundle
            }
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}