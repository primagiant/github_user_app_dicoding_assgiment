package com.primagiant.githubuser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.primagiant.githubuser.data.local.dataStore.SettingPreferences
import com.primagiant.githubuser.model.ThemeModelFactory
import com.primagiant.githubuser.model.ThemeViewModel
import com.primagiant.githubuser.ui.UserListActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel =
            ViewModelProvider(this, ThemeModelFactory(pref))[ThemeViewModel::class.java]
        themeViewModel.getThemeSettings().observe(this) { isDarkMode ->
            toggleDarkMode(isDarkMode)
        }

        var handler = Handler()
        handler.postDelayed({
            nextScreen()
            finish()
        }, 3000)
    }

    private fun nextScreen() {
        val toMainActivity = Intent(this@MainActivity, UserListActivity::class.java)
        startActivity(toMainActivity)
    }

    private fun toggleDarkMode(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}