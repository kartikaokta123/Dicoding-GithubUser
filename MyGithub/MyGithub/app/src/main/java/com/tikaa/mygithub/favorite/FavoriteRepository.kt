package com.tikaa.mygithub.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import com.tikaa.mygithub.data.entity.FavoriteEntity
import com.tikaa.mygithub.data.room.FavoriteUserDao
import com.tikaa.mygithub.data.room.UserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository (application: Application) {
    private var favoriteDao: FavoriteUserDao
    private var userDb: UserDatabase

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        userDb = UserDatabase.getDatabase(application)
        favoriteDao = userDb.favoriteUserDao()
    }

    fun getFavorite(): LiveData<List<FavoriteEntity>> {
        return favoriteDao.getFavoriteUser()
    }


    fun insertFavorite(username: String, id: Int, avatarUrl: String) {
        executorService.execute {
            var user = FavoriteEntity(
                username,
                id,
                avatarUrl
            )
            favoriteDao.insertFavorite(user)
        }
    }

    fun checkUser(id: Int) = favoriteDao.checkUser(id)

    fun deleteFavorite(id: Int) {
        executorService.execute{ favoriteDao.deleteFavorite(id) }

    }
}