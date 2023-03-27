package com.example.themovieapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovieapp.databinding.ActivityMainBinding
import com.example.themovieapp.viewmodel.MainViewModel

class MainActivity: AppCompatActivity(), MovieAdapter.SetOnItemClicked {
    private lateinit var binding: ActivityMainBinding
    private lateinit var popularAdapter: MovieAdapter
    private lateinit var topRatedAdapter: MovieAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSearchView()
        initViewModel()
        initRv()
        setObserver()
    }

    private fun initSearchView() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {return true}

        })
    }

    private fun setObserver() {
        viewModel.getPopularMovieData().observe(this) {
            if (it == null) return@observe
            if (it.errorMsg.isNotBlank()) {
                Toast.makeText(this, it.errorMsg, Toast.LENGTH_SHORT).show()
                return@observe
            }
            popularAdapter.updateList(it.result)
            setProgressBar(it.loadingStatus)
        }

        viewModel.getTopRatedMovieData().observe(this) {
            if (it == null) return@observe
            if (it.errorMsg.isNotBlank()) {
                Toast.makeText(this, it.errorMsg, Toast.LENGTH_SHORT).show()
                return@observe
            }
            topRatedAdapter.updateList(it.result)
            setProgressBar(it.loadingStatus)
        }
    }

    private fun setProgressBar(loadingStatus: Boolean) {
        binding.loadingProgress.isVisible = loadingStatus
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun initRv() {
        binding.rvPopularListMovie.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        popularAdapter = MovieAdapter(mutableListOf(), this)
        binding.rvPopularListMovie.adapter = popularAdapter

        binding.rvTopRatedMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topRatedAdapter = MovieAdapter(mutableListOf(), this)
        binding.rvTopRatedMovies.adapter = topRatedAdapter
    }

    override fun onItemClicked(position: Int) {
        Utils.initIntent(this, MovieDetailsActivity::class.java, popularAdapter.getId(position))
    }
}