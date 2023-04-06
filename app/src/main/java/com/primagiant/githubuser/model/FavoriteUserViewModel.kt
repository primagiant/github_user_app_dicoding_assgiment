package com.primagiant.githubuser.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.primagiant.githubuser.data.FavoriteUserRepository

class FavoriteUserViewModel(
    private val favoriteUserRepository: FavoriteUserRepository
) : ViewModel() {

    fun getFavoriteUser() = favoriteUserRepository.getFavoriteUser()

    fun isFavorite(username: String) = favoriteUserRepository.isFavorite(username)

    fun insertToFavorite(username: String, avatarUrl: String) {
        favoriteUserRepository.insertUserToFavorite(username, avatarUrl)
    }

    fun deleteFromFavorite(username: String) {
        favoriteUserRepository.deleteUserFromFavorite(username)
    }

}