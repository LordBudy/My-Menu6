package com.example.mymenu.Data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mymenu.Data.ModelsEntitys.BasketItemEntity
import com.example.mymenu.Data.ModelsEntitys.DishEntity
import kotlinx.coroutines.flow.Flow

//интерфейс -описывает методы которые будут использоваться для доступа к данным
@Dao // Аннотация @Dao указывает, что это Data Access Object (DAO) - интерфейс для доступа к данным базы данных Room
interface BasketDao {
        // Методы для работы с корзиной (BasketItemEntity)

        @Insert(onConflict = OnConflictStrategy.REPLACE) // Аннотация @Insert для вставки данных в таблицу
        suspend fun insertBasketItem(item: BasketItemEntity) // Метод для вставки одного элемента корзины в БД.

        @Query("SELECT * FROM basket_item") // Аннотация @Query для выполнения SQL-запроса
        fun getAllBasketItems(): Flow<List<BasketItemEntity>> // Метод для получения всех элементов корзины из БД. Возвращает Flow, чтобы получать обновления данных асинхронно.

        @Query("SELECT * FROM basket_item WHERE id = :itemId") // Аннотация @Query для выполнения SQL-запроса
        suspend fun getBasketItemById(itemId: Int): BasketItemEntity? // Метод для получения элемента корзины по его ID из БД.

        @Delete // Аннотация @Delete для удаления данных из таблицы
        suspend fun deleteBasketItem(item: BasketItemEntity) // Метод для удаления элемента корзины из БД.

        // Для работы с блюдами (DishEntity) - если они вам нужны
        @Insert(onConflict = OnConflictStrategy.REPLACE) // Аннотация @Insert для вставки данных в таблицу
        suspend fun insertDish(dish: DishEntity) // Метод для вставки одного блюда в БД.

        @Query("SELECT * FROM dish") // Аннотация @Query для выполнения SQL-запроса
        fun getAllDishes(): Flow<List<DishEntity>> // Метод для получения всех блюд из БД. Возвращает Flow, чтобы получать обновления данных асинхронно.

        // МЕТОДЫ ДЛЯ ИЗМЕНЕНИЯ КОЛИЧЕСТВА БЛЮД В КОРЗИНЕ

        @Update // Аннотация @Update для обновления данных в таблице
        suspend fun updateBasket(dishs: DishEntity)//обновит бд // Метод для обновления информации о блюде в корзине.

        @Delete // Аннотация @Delete для удаления данных из таблицы
        suspend fun deleteBasketItem(dishs: DishEntity) // Удаляем DishEntity // Метод для удаления блюда из корзины.
}