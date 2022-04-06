package com.tikaa.mygithub.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tikaa.mygithub.API.APIConfig
import com.tikaa.mygithub.data.UserResponse
import com.tikaa.mygithub.data.entity.FavoriteEntity
import com.tikaa.mygithub.favorite.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    private val mFavRepository : FavoriteRepository = FavoriteRepository(application)
    private val _user = MutableLiveData<UserResponse>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val user: LiveData<UserResponse> = _user

    companion object {
        private const val TAG = "MainPageGithub"
    }

    fun setDetail(username: String) {
        _isLoading.value = true
        val client2 = APIConfig.getAPIService().getDetail(username)
        client2.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    Log.e(DetailUserViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailUserViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun insertFavorite(username: String, id: Int, avatarUrl: String) {
        mFavRepository.insertFavorite(username, id, avatarUrl)
    }

    fun checkUser(id: Int) = mFavRepository.checkUser(id)

    fun deleteFavorite(id: Int) = mFavRepository.deleteFavorite(id)

}