package com.example.sharingsessionpaging.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharingsessionpaging.databinding.ActivityMainBinding
import com.example.sharingsessionpaging.ui.adapter.MovieAdapter
import com.example.sharingsessionpaging.ui.adapter.MovieLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        movieAdapter = MovieAdapter()

        mainBinding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter { movieAdapter.retry() }
            )
        }

        lifecycleScope.launch {
            viewModel.movies.collectLatest {
                movieAdapter.submitData(it)
            }
        }

        mainBinding.btnRetry.setOnClickListener {
            movieAdapter.retry()
        }

        movieAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                mainBinding.btnRetry.visibility = View.GONE
                mainBinding.progressBar.visibility = View.VISIBLE
            } else {
                mainBinding.progressBar.visibility = View.GONE
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        mainBinding.btnRetry.visibility = View.VISIBLE
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
