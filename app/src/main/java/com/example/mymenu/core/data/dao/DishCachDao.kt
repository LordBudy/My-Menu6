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

    @Query("SELECT * FROM cachs")
    fun getAllDishes(): Flow<List<DishCachEntity>> // Получить все блюда из кэша

    @Query("SELECT * FROM cachs WHERE id = :dishId")
    suspend fun getDishById(dishId: Int): DishCachEntity? // Получить блюдо по ID

    @Update
    suspend fun updateDish(dish: DishCachEntity) // Метод для обновления блюда в кэше

    @Query("DELETE FROM cachs WHERE id = :dishId")
    suspend fun deleteDishById(dishId: Int) // Метод для удаления блюда из кэша
}