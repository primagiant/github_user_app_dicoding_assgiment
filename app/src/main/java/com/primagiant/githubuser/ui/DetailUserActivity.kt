package com.primagiant.githubuser.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.primagiant.githubuser.R
import com.primagiant.githubuser.data.remote.response.DetailUserResponse
import com.primagiant.githubuser.databinding.ActivityDetailUserBinding
import com.primagiant.githubuser.model.FavoriteUserModelFactory
import com.primagiant.githubuser.model.FavoriteUserViewModel
import com.primagiant.githubuser.model.UserViewModel
import com.primagiant.githubuser.ui.adapter.DetailUserPagerAdapter

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val userViewModel: UserViewModel by viewModels()

    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel> {
        FavoriteUserModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_user_title)

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

        if (detailUser != null) {
            favoriteUserViewModel.isFavorite(detailUser.login).observe(this) {
                toggleFavoriteIcon(it)
            }
        }

        binding.addToFav.setOnClickListener {
            if (detailUser != null) {
                toggleFavorite(detailUser.login, detailUser.avatarUrl)
            }
        }

        binding.incDetailUser.apply {
            tvUsername.text = detailUser?.login
            tvName.text = detailUser?.name
            tvFollower.text = this@DetailUserActivity.resources.getString(
                R.string.d_following, detailUser?.following
            )
            tvFollowing.text = this@DetailUserActivity.resources.getString(
                R.string.d_follower, detailUser?.followers
            )
            Glide.with(this@DetailUserActivity).load(detailUser?.avatarUrl).into(avatarImg)
        }
    }

    private fun toggleFavorite(username: String, avatarUrl: String) {
        favoriteUserViewModel.isFavorite(username).observe(this) { disfavour ->
            if (disfavour) {
                favoriteUserViewModel.deleteFromFavorite(username)
                binding.addToFav.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.addToFav.context, R.drawable.ic_baseline_favorite_border_24
                    )
                )
            } else {
                favoriteUserViewModel.insertToFavorite(username, avatarUrl)
                binding.addToFav.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.addToFav.context, R.drawable.ic_baseline_favorite_24
                    )
                )
            }
        }
    }

    private fun toggleFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.addToFav.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.addToFav.context, R.drawable.ic_baseline_favorite_24
                )
            )
        } else {
            binding.addToFav.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.addToFav.context, R.drawable.ic_baseline_favorite_border_24
                )
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (!isLoading) {
            binding.incDetailUser.apply {
                progressBar.visibility = View.GONE
                tvUsername.visibility = View.VISIBLE
                tvName.visibility = View.VISIBLE
                tvFollowing.visibility = View.VISIBLE
                tvFollower.visibility = View.VISIBLE
                avatarImg.visibility = View.VISIBLE
            }
        } else {
            binding.incDetailUser.apply {
                progressBar.visibility = View.VISIBLE
                tvUsername.visibility = View.GONE
                tvName.visibility = View.GONE
                tvFollowing.visibility = View.GONE
                tvFollower.visibility = View.GONE
                avatarImg.visibility = View.GONE
            }
        }
    }

    companion object {
        var USERNAME = "arif"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower, R.string.following
        )
    }

}