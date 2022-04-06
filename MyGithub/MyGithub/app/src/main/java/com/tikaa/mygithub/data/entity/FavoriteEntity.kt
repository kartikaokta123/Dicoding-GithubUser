package com.tikaa.mygithub.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favoriteGithubUser")
@Parcelize
data class FavoriteEntity (
    @field:ColumnInfo(name = "login")
    val login: String,

    @field:ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "avatar_url")
    val avatar_url: String
) : Parcelable