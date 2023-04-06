package com.primagiant.githubuser.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.primagiant.githubuser.data.local.entity.FavoriteUserEntity

@Database(entities = [FavoriteUserEntity::class], version = 1)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {
    abstract fun favoriteUserDAO(): FavoriteUserDAO

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteUserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteUserRoomDatabase::class.java, "favorite_user_db"
                    )
//                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as FavoriteUserRoomDatabase
        }
    }
}