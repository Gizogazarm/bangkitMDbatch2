package com.example.submission1.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    @PrimaryKey(autoGenerate = true) var id_game: Int?,
    @ColumnInfo("username") var username: String,
    @ColumnInfo("avatarUrl") var avatar: String
)
