package com.example.mymenu.Domain.Basket

import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.Flow

// Интерфейс определяет набор методов, которые должны быть реализованы классами,
// работающими с данными корзины. Это позволяет отделить бизнес-логику от конкретной
// реализации доступа к данным база данных
interface BasketRepository {


    // Метод для получения всех блюд. Возвращает Flow, который содержит список DishItem.
    // Flow позволяет получать данные асинхронно и реактивно (автоматически получать обновления данных).
    fun getAllDishes(): Flow<List<DishItem>>

    // Метод для добавляет блюдо в корзину
    // и возвращает добавленное блюдо (DishItem).
    suspend fun addDishToBasket(dishId: Int): DishItem

    // Метод для удаления блюда из корзины. Принимает DishItem (блюдо для удаления).
    suspend fun deleteDishBasket(dish: DishItem)

    // Метод для уменьшения количества блюда в корзине (уменьшение на 1).
    // Принимает id (идентификатор блюда).
    suspend fun minusDish(id: Int): DishItem

    // Метод для увеличения количества блюда в корзине (увеличение на 1).
    // Принимает id (идентификатор блюда).
    suspend fun plusDish(id: Int): DishItem
}