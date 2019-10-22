package com.zendev.searchrepository.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import com.zendev.searchrepository.data.local.entity.Repository
import java.util.concurrent.Executor

class GithubLocalCache(
    private val repoDao: RepositoryDao,
    private val ioExecutor: Executor
) {
    fun insert(repos: List<Repository>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("GithubLocalCache", "inserting ${repos.size} repos")
            repoDao.insert(repos)
            insertFinished()
        }
    }

    fun reposByName(name: String): LiveData<List<Repository>> {
        // appending '%' so we can allow other characters to be before and after the query string
        val query = "%${name.replace(' ', '%')}%"
        return repoDao.reposByName(query)
    }
}