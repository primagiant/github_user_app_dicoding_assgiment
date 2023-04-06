package com.primagiant.githubuser.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.primagiant.githubuser.data.FavoriteUserRepository
import com.primagiant.githubuser.di.Injection

class FavoriteUserModelFactory private constructor(
    private val favoriteUserRepository: FavoriteUserRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            return FavoriteUserViewModel(favoriteUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavoriteUserModelFactory? = null
        fun getInstance(context: Context): FavoriteUserModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteUserModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}