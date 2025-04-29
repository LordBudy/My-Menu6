package com.example.mymenu.Data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mymenu.Data.ModelsEntitys.DishEntity
import kotlinx.coroutines.flow.Flow

@Dao // Аннотация @Dao указывает, что это Data Access Object (DAO) - интерфейс для доступа к данным базы данных Room
interface BasketDao {
    // Методы для работы с корзиной (BasketItemEntity)

    @Delete // Аннотация @Delete для удаления данных из таблицы
    suspend fun deleteBasketItem(item: DishEntity) // Метод для удаления блюда в корзине и из БД

    // Аннотация @Insert для вставки данных в таблицу в БД
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDish(dish: DishEntity) // Метод для вставки одного блюда в БД

    // Аннотация @Query для выполнения SQL-запроса
    @Query("SELECT * FROM dish")
    // Метод для получения всех блюд из БД
    // Возвращает Flow, чтобы получать обновления данных асинхронно
    fun getAllDishs(): Flow<List<DishEntity>>

    // Метод для обновления информации о блюде в корзине
    @Update // Аннотация @Update для обновления данных в таблице
    suspend fun updateBasket(dishs: DishEntity)//обновит бд

}