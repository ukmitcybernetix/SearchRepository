package com.zendev.searchrepository.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.zendev.searchrepository.data.MainRepository
import com.zendev.searchrepository.data.local.GithubLocalCache
import com.zendev.searchrepository.data.local.RepositoryDatabase
import com.zendev.searchrepository.data.remote.GithubService
import com.zendev.searchrepository.viewmodel.ViewModelFactory
import java.util.concurrent.Executors

object Injection {

    private fun provideCache(context: Context): GithubLocalCache {
        val database = RepositoryDatabase.getInstance(context)
        return GithubLocalCache(database.repositoryDao(), Executors.newSingleThreadExecutor())
    }

    private fun provideGithubRepository(context: Context): MainRepository {
        return MainRepository(GithubService.create(), provideCache(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository(context))
    }
}