package com.example.mymenu.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mymenu.core.data.modelsEntitys.DishCachEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DishCachDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDish(dish: DishCachEntity) // Метод для вставки одного блюда в кэш

    @Query("SELECT * FROM cachs_dish")
    fun getAllDishes(): Flow<List<DishCachEntity>> // Получить все блюда из кэша

    @Query("SELECT * FROM cachs_dish WHERE id = :dishId")
    suspend fun getDishById(dishId: Int): DishCachEntity? // Получить блюдо по ID

    @Update
    suspend fun updateDish(dish: DishCachEntity) // Метод для обновления блюда в кэше

    @Query("DELETE FROM cachs_dish")
    suspend fun clearCache()// Метод для удаления кэша
}