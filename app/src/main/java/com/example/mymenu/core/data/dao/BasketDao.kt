package com.example.mymenu.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mymenu.core.data.modelsEntitys.BasketDishEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketDao {
    // Методы для работы с корзиной (DishEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDish(dish: BasketDishEntity) // Метод для вставки одного блюда в БД

    @Query("SELECT * FROM dish")
    fun getAllDishs(): Flow<List<BasketDishEntity>>

    @Query("SELECT * FROM dish WHERE id = :dishId")
    suspend fun getDishById(dishId: Int): BasketDishEntity?

    @Update
    suspend fun updateDish(dish: BasketDishEntity) // Исправлено: метод для обновления DishEntity

    @Query("DELETE FROM dish WHERE id = :dishId")
    suspend fun deleteDishById(dishId: Int?)
}