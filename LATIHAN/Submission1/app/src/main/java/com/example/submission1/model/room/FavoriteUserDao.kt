package com.example.submission1.model.room

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface FavoriteUserDao {

    @Insert
    suspend fun insertFavUser(favoriteUser: FavoriteUser)


}