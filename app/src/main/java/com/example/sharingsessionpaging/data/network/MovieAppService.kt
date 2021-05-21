package com.example.sharingsessionpaging.data.network

import com.example.sharingsessionpaging.data.model.Result
import com.example.sharingsessionpaging.data.network.response.MovieListResponse

class MovieAppService(private val api: Api) : BaseService() {

    suspend fun fetchPopularMovies(page: Int) : Result<MovieListResponse> {
        return createCall { api.getPopularMovies(page, true) }
    }
}
