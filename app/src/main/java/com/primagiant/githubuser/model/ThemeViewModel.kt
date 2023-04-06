package com.primagiant.githubuser.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.primagiant.githubuser.data.local.dataStore.SettingPreferences
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val pref: SettingPreferences
) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkMode)
        }
    }

}