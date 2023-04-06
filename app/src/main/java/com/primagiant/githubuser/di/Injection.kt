package com.primagiant.githubuser.di

import android.content.Context
import com.primagiant.githubuser.data.FavoriteUserRepository
import com.primagiant.githubuser.data.local.database.FavoriteUserRoomDatabase
import com.primagiant.githubuser.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteUserRepository {
        val database = FavoriteUserRoomDatabase.getDatabase(context)
        val dao = database.favoriteUserDAO()
        val appExecutors = AppExecutors()
        return FavoriteUserRepository.getInstance(dao, appExecutors)
    }
}