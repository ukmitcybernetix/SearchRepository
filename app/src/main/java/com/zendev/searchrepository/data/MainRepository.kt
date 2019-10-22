package com.zendev.searchrepository.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.zendev.searchrepository.data.local.GithubLocalCache
import com.zendev.searchrepository.data.local.entity.RepositoryResult
import com.zendev.searchrepository.data.remote.GithubService
import com.zendev.searchrepository.data.remote.searchRepos

class MainRepository(
    private val service: GithubService,
    private val cache: GithubLocalCache
) {
    private var lastRequestedPage = 1
    private val networkErrors = MutableLiveData<String>()
    private var isRequestInProgress = false

    fun search(query: String): RepositoryResult {
        Log.d("GithubRepository", "New query: $query")
        lastRequestedPage = 1
        requestAndSaveData(query)

        val data = cache.reposByName(query)

        return RepositoryResult(data, networkErrors)
    }

    fun requestMore(query: String) {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchRepos(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, { repos ->
            cache.insert(repos) {
                lastRequestedPage++
                isRequestInProgress = false
            }
        }, { error ->
            networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}