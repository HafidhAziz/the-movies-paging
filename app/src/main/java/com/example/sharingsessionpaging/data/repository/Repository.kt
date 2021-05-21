package com.example.sharingsessionpaging.data.repository

import com.example.sharingsessionpaging.data.model.Result
import com.example.sharingsessionpaging.data.network.MovieAppService
import com.example.sharingsessionpaging.data.network.response.MovieListResponse

class Repository(private val service: MovieAppService) {

    suspend fun getPopularMovies(page: Int) : MovieListResponse {
        return when(val result = service.fetchPopularMovies(page)){
            is Result.Success -> result.data
            is Result.Error -> throw result.error
        }
    }
}
