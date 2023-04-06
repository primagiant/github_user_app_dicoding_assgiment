package com.primagiant.githubuser.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.primagiant.githubuser.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteUserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUser: FavoriteUserEntity)

    @Query("DELETE FROM favorite_user WHERE username=:username")
    fun delete(username: String)

    @Query("SELECT * FROM favorite_user")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT EXISTS (SELECT * FROM favorite_user WHERE username=:username)")
    fun isFavoriteUser(username: String): Boolean

}