package com.primagiant.githubuser.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.primagiant.githubuser.data.local.dataStore.SettingPreferences

class ThemeModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}