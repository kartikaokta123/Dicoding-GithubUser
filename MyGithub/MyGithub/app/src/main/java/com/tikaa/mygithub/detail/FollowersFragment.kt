package com.tikaa.mygithub.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tikaa.mygithub.main.MainAdapter
import com.tikaa.mygithub.viewModel.MainViewModel
import com.tikaa.mygithub.R
import com.tikaa.mygithub.data.User
import com.tikaa.mygithub.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var data: String
    private val list = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        data = args?.getString(DetailUserGithub.DATA).toString()

        adapter = MainAdapter(list)
        adapter.notifyDataSetChanged()


        binding.rvFollowers.setHasFixedSize(true)
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.adapter = adapter

        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        viewModel.setFollowers(data)
        viewModel.listUsers2.observe(viewLifecycleOwner, { followers ->
            followers?.let {
                if (followers.isEmpty()){
                    binding.noFollowers.visibility = View.VISIBLE
                    binding.personFollow.visibility = View.VISIBLE
                } else {
                    adapter.setList(it)
                    showLoading(false)
                }
            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val intent = Intent(context, DetailUserGithub::class.java)
                intent.putExtra(DetailUserGithub.DATA, user.login)
                intent.putExtra(DetailUserGithub.ID, user.id)
                intent.putExtra(DetailUserGithub.AVATAR, user.avatar_url)
                startActivity(intent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}