package com.dicoding.githubuserproject.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserproject.R
import com.dicoding.githubuserproject.adapter.ListAdapter
import com.dicoding.githubuserproject.databinding.ActivityMainBinding
import com.dicoding.githubuserproject.model.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ListAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMenu)

        adapter = ListAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.getObserver().observe(this, {
            if (it != null) {
                if (it.size == 0) {
                    binding.recyclerView.visibility = View.GONE
                    binding.errorLayout.errorMessage.visibility = View.VISIBLE
                    binding.errorLayout.errorMessage.text = getString(R.string.user_not_found)
                } else {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.errorLayout.errorMessage.visibility = View.GONE
                    adapter.updatedData(it)
                }
                showLoading(false)
            } else {
                Toast.makeText(this, "Error in getting data", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        })
    }
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_button, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                mainViewModel.setObserver(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.icon_menu -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }

            R.id.icon_favourite -> {
                val faveList = Intent(this@MainActivity, FavouriteActivity::class.java)
                startActivity(faveList)
            }

            R.id.icon_setting -> {
                val settings = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(settings)
            }
        }
    }
}
