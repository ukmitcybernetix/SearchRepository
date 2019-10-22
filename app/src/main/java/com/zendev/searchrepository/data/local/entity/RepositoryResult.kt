package com.zendev.searchrepository.data.local.entity

import androidx.lifecycle.LiveData

data class RepositoryResult (
    val data: LiveData<List<Repository>>,
    val networkErrors: LiveData<String>
)