package com.myAllVideoBrowser.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myAllVideoBrowser.data.local.room.entity.VideoInfo

@Dao
interface VideoDao {

    @Query("SELECT * FROM VideoInfo WHERE originalUrl = :url")
    suspend fun getVideoById(url: String): VideoInfo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(videoInfo: VideoInfo)
}