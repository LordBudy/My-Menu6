package com.example.mymenu.Domain.Basket

import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.Flow

// Интерфейс определяет набор методов, которые должны быть реализованы классами,
// работающими с данными корзины. Это позволяет отделить бизнес-логику от конкретной
// реализации доступа к данным база данных
interface BasketRepository {

    fun getAllDishes(): Flow<List<DishItem>>

    suspend fun deleteDishBasket(dishId: Int)

    suspend fun minusDish(id: Int): DishItem

    suspend fun plusDish(id: Int): DishItem

    suspend fun addDishToBasket(dish: Int): DishItem
}