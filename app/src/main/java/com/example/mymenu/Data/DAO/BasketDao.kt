package com.example.mymenu.Data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mymenu.Data.ModelsEntitys.DishEntity
import kotlinx.coroutines.flow.Flow

//интерфейс -описывает методы которые будут использоваться для доступа к данным
@Dao
interface BasketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBasket(dishes: DishEntity)//добавит в бд

    @Query("SELECT * FROM dishs")
    fun getAllBasket(): Flow<List<DishEntity>>//выведет весь список в бд

    @Query("SELECT * FROM dishs WHERE id_dish = :dishId")//выведет одно блюдо по id
    fun getBasketItem(dishId: Int) : Flow<DishEntity?>

    @Update
    suspend fun updateBasket(dishs: DishEntity)//обновит бд

    @Delete
    suspend fun deleteBasketItem(dishes: DishEntity) // Удаляем DishEntity
//    @Query("DELETE FROM dishes WHERE id_dish = :dishId")//удалить один обьект из бд
//    suspend fun deleteBasketItem(dishId: Int)

}