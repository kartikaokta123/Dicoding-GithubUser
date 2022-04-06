package com.tikaa.mygithub.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tikaa.mygithub.data.User
import com.tikaa.mygithub.data.entity.FavoriteEntity
import com.tikaa.mygithub.databinding.ActivityFavoriteGithubUserBinding
import com.tikaa.mygithub.detail.DetailUserGithub
import com.tikaa.mygithub.main.MainAdapter
import com.tikaa.mygithub.viewModel.FavoriteViewModel

class FavoriteGithubUser : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteGithubUserBinding
    private lateinit var adapter: MainAdapter
    private val list = ArrayList<User>()
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteGithubUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Favorite User"
        showRecyclerList()

    }

    private fun showRecyclerList(){
        adapter = MainAdapter(list)
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this, FavoriteViewModelFactory(application)).get(
            FavoriteViewModel::class.java
        )

        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intentToDetail = Intent(this@FavoriteGithubUser, DetailUserGithub::class.java)
                intentToDetail.putExtra(DetailUserGithub.DATA, data.login)
                intentToDetail.putExtra(DetailUserGithub.ID, data.id)
                intentToDetail.putExtra(DetailUserGithub.AVATAR, data.avatar_url)
                startActivity(intentToDetail)
            }
        })
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.adapter = adapter
        binding.rvUser.setHasFixedSize(true)

        viewModel.getFavoriteUser().observe(this, { favoriteList ->
            if (favoriteList.isEmpty()){
                binding.imgFavorite.visibility = View.VISIBLE
                binding.addFavorite.visibility = View.VISIBLE
            }
            if (favoriteList!=null){
                val listFavo = mapList(favoriteList)
                adapter.setList(listFavo)

            }
        })
    }

    private fun mapList(favorite: List<FavoriteEntity>): ArrayList<User> {
        val listFavorite = ArrayList<User>()
        for (user in favorite) {
            val userMapped = user.login?.let {
                user.avatar_url?.let { it1 ->
                    User(
                        it,
                        user.id,
                        it1
                    )
                }
            }
            if (userMapped != null) {
                listFavorite.add(userMapped)
            }
        }
        return listFavorite
    }

}