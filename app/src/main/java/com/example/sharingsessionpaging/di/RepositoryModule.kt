package com.example.sharingsessionpaging.di

import com.example.sharingsessionpaging.data.network.MovieAppService
import com.example.sharingsessionpaging.data.repository.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single { createRepository(get()) }
}

fun createRepository(
    movieAppService: MovieAppService
) : Repository = Repository(movieAppService)
