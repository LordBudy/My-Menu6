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
@Dao
interface BasketDao {
        // Методы для работы с корзиной (BasketItemEntity)
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertBasketItem(item: BasketItemEntity)

        @Query("SELECT * FROM basket_item") // Исправлено
        fun getAllBasketItems(): Flow<List<BasketItemEntity>>

        @Query("SELECT * FROM basket_item WHERE id = :itemId") // Исправлено
        suspend fun getBasketItemById(itemId: Int): BasketItemEntity?

        @Delete
        suspend fun deleteBasketItem(item: BasketItemEntity)

        // Для работы с блюдами (DishEntity) - если они вам нужны
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertDish(dish: DishEntity)

        @Query("SELECT * FROM dish")
        fun getAllDishes(): Flow<List<DishEntity>>
        /* УДАЛИТЕ ЭТИ МЕТОДЫ, ОНИ НЕ НУЖНЫ
        @Query("SELECT * FROM dish") // ИСПОЛЬЗУЕТСЯ НЕ ВЕРНАЯ ТАБЛИЦА
        fun getAllBasket(): Flow<List<DishEntity>>//выведет весь список в бд

        @Query("SELECT * FROM dish WHERE id_dish = :dishId")//выведет одно блюдо по id
        fun getBasketItem(dishId: Int) : Flow<DishEntity?>
        */
        // МЕТОДЫ ДЛЯ ИЗМЕНЕНИЯ КОЛИЧЕСТВА БЛЮД В КОРЗИНЕ

        @Update
        suspend fun updateBasket(dishs: DishEntity)//обновит бд

        @Delete
        suspend fun deleteBasketItem(dishs: DishEntity) // Удаляем DishEntity
    }