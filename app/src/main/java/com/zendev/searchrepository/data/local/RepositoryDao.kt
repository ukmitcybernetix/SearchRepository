package com.zendev.searchrepository.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zendev.searchrepository.data.local.entity.Repository

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Repository>)

    @Query("SELECT * FROM repos WHERE (name LIKE :queryString) OR (description LIKE " +
            ":queryString) ORDER BY stars DESC, name ASC")
    fun reposByName(queryString: String): LiveData<List<Repository>>
}