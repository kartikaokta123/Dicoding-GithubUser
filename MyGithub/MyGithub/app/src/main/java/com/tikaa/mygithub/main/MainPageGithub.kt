package com.tikaa.mygithub.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tikaa.mygithub.viewModel.MainViewModel
import com.tikaa.mygithub.R
import com.tikaa.mygithub.data.User
import com.tikaa.mygithub.databinding.ActivityMainPageGithubBinding
import com.tikaa.mygithub.detail.DetailUserGithub
import com.tikaa.mygithub.favorite.FavoriteGithubUser
import com.tikaa.mygithub.theme.*


class MainPageGithub : AppCompatActivity() {

    private val list = ArrayList<User>()
    private lateinit var binding: ActivityMainPageGithubBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar!!.title = "Github User's"

        showRecyclerList()
        viewModel.setListUser()

    }

    private fun SearchUser() {
        val search = binding.edSearch.text.toString()
        if (search.isEmpty()) return
        showLoading(true)
        viewModel.setSearch(search)
    }


    private fun showRecyclerList() {
        val mainAdapter = MainAdapter(list)
        mainAdapter.notifyDataSetChanged()
        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager
        binding.rvGithub.adapter = mainAdapter
        binding.rvGithub.setHasFixedSize(true)

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithub.addItemDecoration(itemDecoration)

        binding.edSearch.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                SearchUser()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        viewModel.listUsers2.observe(this, {
            if (it != null) {
                mainAdapter.setList(it)
                showLoading(false)
            }
        })
        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        mainAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intentToDetail = Intent(this@MainPageGithub, DetailUserGithub::class.java)
                intentToDetail.putExtra(DetailUserGithub.DATA, data.login)
                intentToDetail.putExtra(DetailUserGithub.ID, data.id)
                intentToDetail.putExtra(DetailUserGithub.AVATAR, data.avatar_url)
                startActivity(intentToDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val i = Intent(this, Setting_Theme::class.java)
                startActivity(i)
                return true
            }
            R.id.favorite -> {
                val i = Intent(this, FavoriteGithubUser::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }
}