package com.primagiant.githubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.primagiant.githubuser.R
import com.primagiant.githubuser.ui.fragment.HomeFragment


class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val fragmentManager = supportFragmentManager
        val homeFragment = HomeFragment()
        val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (fragment !is HomeFragment) {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName
                ).commit()
        }
    }

}