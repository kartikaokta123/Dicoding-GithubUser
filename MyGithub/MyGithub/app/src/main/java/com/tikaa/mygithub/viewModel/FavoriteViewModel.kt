package com.tikaa.mygithub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.tikaa.mygithub.data.entity.FavoriteEntity
import com.tikaa.mygithub.favorite.FavoriteRepository

class FavoriteViewModel (application: Application) : AndroidViewModel(application) {

    private val mFavRepository : FavoriteRepository = FavoriteRepository(application)
    fun getFavoriteUser() : LiveData<List<FavoriteEntity>> = mFavRepository.getFavorite()
}