package com.example.mymenu.Domain.Basket

import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.Flow

interface BasketRepository {

    fun getAllDishes(): Flow<List<DishItem>>      // Получаем все блюда
    suspend fun addDishToBasket(dishId: Int): DishItem // Добавляем блюдо в корзину
    suspend fun deleteDishBasket(dish: DishItem) //удалит одно блюдо
    suspend fun minusDish(id: Int): DishItem
    suspend fun plusDish(id: Int): DishItem //Прибавляет кол-во блюд в корзине +1
}