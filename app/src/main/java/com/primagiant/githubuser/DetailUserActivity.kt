package com.primagiant.githubuser

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.primagiant.githubuser.adapter.DetailUserPagerAdapter
import com.primagiant.githubuser.databinding.ActivityDetailUserBinding
import com.primagiant.githubuser.model.UserViewModel
import com.primagiant.githubuser.response.DetailUserResponse

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val userViewModel: UserViewModel by viewModels()

    companion object {
        var USERNAME = "arif"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower, R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Github User"

        val username = intent?.getStringExtra(USERNAME)
        if (username != null) {
            userViewModel.getDetailUser(username)
        }

        val detailUserPagerAdapter = DetailUserPagerAdapter(this, username)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)

        viewPager.adapter = detailUserPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        userViewModel.isLoading.observe(this) { loading ->
            showLoading(loading)
        }

        userViewModel.detailUser.observe(this) {
            getDetailUser(it)
        }
    }

    private fun getDetailUser(detailUser: DetailUserResponse?) {
        val cFollowing =
            this@DetailUserActivity.resources.getString(R.string.d_following, detailUser?.following)
        val cFollower =
            this@DetailUserActivity.resources.getString(R.string.d_follower, detailUser?.followers)

        binding.tvUsername.text = detailUser?.login
        binding.tvName.text = detailUser?.name
        binding.tvFollower.text = cFollower
        binding.tvFollowing.text = cFollowing
        Glide.with(this).load(detailUser?.avatarUrl).into(binding.avatarImg)
    }

    private fun showLoading(isLoading: Boolean) {
        if (!isLoading) {
            binding.progressBar.visibility = View.GONE
            binding.tvUsername.visibility = View.VISIBLE
            binding.tvName.visibility = View.VISIBLE
            binding.tvFollowing.visibility = View.VISIBLE
            binding.tvFollower.visibility = View.VISIBLE
            binding.avatarImg.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.VISIBLE
            binding.tvUsername.visibility = View.GONE
            binding.tvName.visibility = View.GONE
            binding.tvFollowing.visibility = View.GONE
            binding.tvFollower.visibility = View.GONE
            binding.avatarImg.visibility = View.GONE
        }
    }

}