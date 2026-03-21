package com.myAllVideoBrowser.data.local.room.dao

import androidx.room.*
import com.myAllVideoBrowser.data.local.room.entity.ProgressInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {

    @Query("SELECT * FROM ProgressInfo")
    fun getProgressInfos(): Flow<List<ProgressInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgressInfo(progressInfo: ProgressInfo)

    @Delete
    suspend fun deleteProgressInfo(progressInfo: ProgressInfo)
}