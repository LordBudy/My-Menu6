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
    // Для работы с корзиной (BasketItemEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBasketItem(item: BasketItemEntity)
    @Query("SELECT * FROM basket_item")
    fun getAllBasketItems(): Flow<List<BasketItemEntity>>
    @Query("SELECT * FROM basket_item WHERE id = :itemId")
    suspend fun getBasketItemById(itemId: Int): BasketItemEntity?

    @Delete
    suspend fun deleteBasketItem(item: BasketItemEntity)

    // Для работы с блюдами (DishEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDish(dish: DishEntity)

    @Query("SELECT * FROM dish")
    fun getAllDishes(): Flow<List<DishEntity>>
    @Query("SELECT * FROM dish")
    fun getAllBasket(): Flow<List<DishEntity>>//выведет весь список в бд

    @Query("SELECT * FROM dish WHERE id_dish = :dishId")//выведет одно блюдо по id
    fun getBasketItem(dishId: Int) : Flow<DishEntity?>

    @Update
    suspend fun updateBasket(dishs: DishEntity)//обновит бд

    @Delete
    suspend fun deleteBasketItem(dishs: DishEntity) // Удаляем DishEntity

}