package com.example.mymenu.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymenu.core.data.modelsEntitys.CategoryCachEntity

@Dao
interface CategoryCachDao {
    @Query("SELECT * FROM cach_cat WHERE id = :itemId LIMIT 1")
    suspend fun getItemById(itemId: Int): CategoryCachEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CategoryCachEntity)

    @Query("SELECT * FROM cach_cat")
    suspend fun getAllItems(): List<CategoryCachEntity>

    @Query("SELECT COUNT(*) FROM cach_cat")
    suspend fun getItemCount(): Int

}