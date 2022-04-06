package com.tikaa.mygithub.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tikaa.mygithub.data.entity.FavoriteEntity

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favoriteGithubUser")
    fun getFavoriteUser(): LiveData<List<FavoriteEntity>>

    @Query("SELECT count(*) FROM favoriteGithubUser WHERE favoriteGithubUser.id = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM favoriteGithubUser WHERE favoriteGithubUser.id = :id")
    fun deleteFavorite(id: Int): Int

}