package com.tikaa.mygithub.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tikaa.mygithub.R
import com.tikaa.mygithub.SectionsPagerAdapter
import com.tikaa.mygithub.data.entity.FavoriteEntity
import com.tikaa.mygithub.databinding.DetailUserGithubBinding
import com.tikaa.mygithub.favorite.FavoriteGithubUser
import com.tikaa.mygithub.theme.Setting_Theme
import com.tikaa.mygithub.viewModel.DetailUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserGithub : AppCompatActivity() {

    private lateinit var binding: DetailUserGithubBinding

    private lateinit var viewModel: DetailUserViewModel

    companion object {
        const val DATA = "DATA"
        const val ID = "ID"
        const val AVATAR = "AVATAR"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailUserGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Detail User"

        val data = intent.getStringExtra(DATA)
        val id = intent.getIntExtra(ID, 0)
        val avatarUrl = intent.getStringExtra(AVATAR)

        val bundle = Bundle()
        bundle.putString(DATA, data)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        if (data != null) {
            viewModel.setDetail(data)
        }

        viewModel.user.observe(this, {
            if (it != null) {
                binding.itemDetail.visibility = View.VISIBLE
                binding.btnFavorite.visibility = View.VISIBLE
                binding.tvItemName.text = it.name
                binding.tvItemUsername.text = it.login
                binding.tvItemFollowers.text = it.followers.toString()
                binding.tvItemFollowing.text = it.following.toString()
                binding.tvItemRepository.text = it.publicRepos.toString()
                binding.tvItemLocation.text = it.location
                binding.tvItemCompany.text = it.company
                Glide.with(this@DetailUserGithub)
                    .load(it.avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.imgItemPhoto)
                showLoading(false)
            } else {
                binding.itemDetail.visibility = View.INVISIBLE
                binding.btnFavorite.visibility = View.GONE
                showLoading(true)
            }
        })
        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        var isFavorite = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.btnFavorite.isChecked = true
                        isFavorite = true
                    } else {
                        binding.btnFavorite.isChecked = false
                        isFavorite = false
                    }
                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                if (data != null) {
                    if (avatarUrl != null) {
                        viewModel.insertFavorite(data, id, avatarUrl)
                        Toast.makeText(
                            this@DetailUserGithub,
                            "Success add to Favorite",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                viewModel.deleteFavorite(id)
                Toast.makeText(
                    this@DetailUserGithub,
                    "Success delete Favorite",
                    Toast.LENGTH_LONG
                ).show()
            }
            binding.btnFavorite.isChecked = isFavorite
        }

        val sectionPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        val viewPager: ViewPager = binding.viewPager2
        viewPager.adapter = sectionPagerAdapter
        binding.tabs.setupWithViewPager(viewPager)
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


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
            binding.itemDetail.visibility = View.INVISIBLE
            binding.btnFavorite.visibility = View.GONE
        } else {
            binding.progressBar3.visibility = View.GONE
        }
    }

}

