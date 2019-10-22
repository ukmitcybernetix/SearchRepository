package com.zendev.searchrepository.data.remote

import com.google.gson.annotations.SerializedName
import com.zendev.searchrepository.data.local.entity.Repository

data class RepositoryResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<Repository> = emptyList(),
    val nextPage: Int? = null
)