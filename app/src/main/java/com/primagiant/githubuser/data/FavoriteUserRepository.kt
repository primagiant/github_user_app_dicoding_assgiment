package com.primagiant.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.primagiant.githubuser.data.local.database.FavoriteUserDAO
import com.primagiant.githubuser.data.local.entity.FavoriteUserEntity
import com.primagiant.githubuser.utils.AppExecutors

class FavoriteUserRepository private constructor(
    private val favoriteUserDAO: FavoriteUserDAO, private val appExecutors: AppExecutors
) {

    private val result = MediatorLiveData<Result<List<FavoriteUserEntity>>>()
    private val isFavoriteResult = MediatorLiveData<Boolean>()

    fun getFavoriteUser(): LiveData<Result<List<FavoriteUserEntity>>> {
        result.value = Result.Loading

        val localData = favoriteUserDAO.getAllFavoriteUser()
        result.addSource(localData) { newData: List<FavoriteUserEntity> ->
            result.value = Result.Success(newData)
        }

        return result
    }

    fun insertUserToFavorite(username: String, avatarUrl: String) {
        appExecutors.diskIO.execute {
            val favUser = FavoriteUserEntity(username, avatarUrl)
            favoriteUserDAO.insert(favUser)
        }
    }

    fun isFavorite(username: String): LiveData<Boolean> {
        appExecutors.diskIO.execute {
            isFavoriteResult.postValue(favoriteUserDAO.isFavoriteUser(username))
        }
        return isFavoriteResult
    }

    fun deleteUserFromFavorite(username: String) {
        appExecutors.diskIO.execute {
            favoriteUserDAO.delete(username)
        }
    }

    companion object {
        @Volatile
        private var instance: FavoriteUserRepository? = null
        fun getInstance(
            favoriteUserDAO: FavoriteUserDAO, appExecutors: AppExecutors
        ): FavoriteUserRepository = instance ?: synchronized(this) {
            instance ?: FavoriteUserRepository(favoriteUserDAO, appExecutors)
        }.also { instance = it }
    }

}
