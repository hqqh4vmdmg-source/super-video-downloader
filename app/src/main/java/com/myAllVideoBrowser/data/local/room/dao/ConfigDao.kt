package com.myAllVideoBrowser.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myAllVideoBrowser.data.local.room.entity.PageInfo
import com.myAllVideoBrowser.data.local.room.entity.SupportedPage

@Dao
interface ConfigDao {

    @Query("SELECT * FROM PageInfo")
    suspend fun getAllTopPages(): List<PageInfo>

    @Query("SELECT * FROM SupportedPage")
    suspend fun getSupportedPages(): List<SupportedPage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPage(pageInfo: PageInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupportedPage(supportedPage: SupportedPage)
}