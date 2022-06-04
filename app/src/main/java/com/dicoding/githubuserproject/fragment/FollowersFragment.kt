package com.dicoding.githubuserproject.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserproject.R
import com.dicoding.githubuserproject.activity.MoveDetailActivity
import com.dicoding.githubuserproject.adapter.ListAdapter
import com.dicoding.githubuserproject.databinding.FragmentFollowersBinding
import com.dicoding.githubuserproject.model.FollowersViewModel

class FollowersFragment : Fragment(R.layout.fragment_followers) {
    private var bindingFragment: FragmentFollowersBinding? = null
    private val binding get() = bindingFragment as FragmentFollowersBinding
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: ListAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(MoveDetailActivity.EXTRA_USERNAME).toString()

        bindingFragment = FragmentFollowersBinding.bind(view)

        adapter = ListAdapter()

        binding.apply {
            followersList.setHasFixedSize(true)
            followersList.layoutManager = LinearLayoutManager(activity)
            followersList.adapter = adapter
        }
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersViewModel::class.java)
        viewModel.setFollowersList(username)
        viewModel.getFollowersList().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.updatedData(it)
                showLoading(false)
            }
        })
        showLoading(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingFragment = null
    }

}