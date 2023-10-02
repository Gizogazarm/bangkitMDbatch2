package com.example.submission1.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {

    @Insert
    suspend fun insertFavUser(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user WHERE username = :username")
    fun getFavoriteByUsername(username: String): LiveData<FavoriteUser>


}