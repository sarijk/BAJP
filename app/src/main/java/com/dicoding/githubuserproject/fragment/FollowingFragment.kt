package com.dicoding.githubuserproject.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserproject.R
import com.dicoding.githubuserproject.adapter.ListAdapter
import com.dicoding.githubuserproject.databinding.FragmentFollowingBinding
import com.dicoding.githubuserproject.model.FollowingViewModel
import com.dicoding.githubuserproject.activity.MoveDetailActivity

class FollowingFragment : Fragment(R.layout.fragment_following) {
    private var bindingFragment: FragmentFollowingBinding? = null
    private val binding get() = bindingFragment as FragmentFollowingBinding
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: ListAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(MoveDetailActivity.EXTRA_USERNAME).toString()

        bindingFragment = FragmentFollowingBinding.bind(view)

        adapter = ListAdapter()

        binding.apply {
            followingList.setHasFixedSize(true)
            followingList.layoutManager = LinearLayoutManager(activity)
            followingList.adapter = adapter
        }
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
        viewModel.setFollowingList(username)
        viewModel.getFollowingList().observe(viewLifecycleOwner, {
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