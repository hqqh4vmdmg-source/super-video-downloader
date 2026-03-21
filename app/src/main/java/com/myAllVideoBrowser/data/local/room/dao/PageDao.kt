package com.myAllVideoBrowser.data.local.room.dao

import androidx.room.*
import com.myAllVideoBrowser.data.local.room.entity.PageInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface PageDao {

    @Query("SELECT * FROM PageInfo ORDER BY `order` ASC")
    fun getPageInfos(): Flow<List<PageInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgressInfo(progressInfo: PageInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProgressInfo(progressInfos: List<PageInfo>)

    @Delete
    suspend fun deleteProgressInfo(progressInfo: PageInfo)

    @Query("DELETE FROM PageInfo")
    suspend fun deleteAll()
}