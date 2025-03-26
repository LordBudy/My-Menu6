package com.example.mymenu.Domain.Basket

import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.Flow

// Объявляем интерфейс BasketRepository
// Интерфейс определяет контракт (набор методов), которые должны быть реализованы классами,
// работающими с данными корзины. Это позволяет отделить бизнес-логику от конкретной
// реализации доступа к данным (например, база данных, сеть).
interface BasketRepository {

    // fun getAllDishes(): Flow<List<DishItem>>
    // Метод для получения всех блюд. Возвращает Flow, который содержит список DishItem.
    // Flow позволяет получать данные асинхронно и реактивно (автоматически получать обновления данных).
    fun getAllDishes(): Flow<List<DishItem>>      // Получаем все блюда

    // suspend fun addDishToBasket(dishId: Int): DishItem
    // Метод для добавления блюда в корзину. Принимает dishId (идентификатор блюда)
    // и возвращает добавленное блюдо (DishItem).
    // suspend указывает, что метод является корутиной (может быть приостановлен и возобновлен).
    suspend fun addDishToBasket(dishId: Int): DishItem // Добавляем блюдо в корзину

    // suspend fun deleteDishBasket(dish: DishItem)
    // Метод для удаления блюда из корзины. Принимает DishItem (блюдо для удаления).
    // suspend указывает, что метод является корутиной.
    suspend fun deleteDishBasket(dish: DishItem) //удалит одно блюдо

    // suspend fun minusDish(id: Int): DishItem
    // Метод для уменьшения количества блюда в корзине (уменьшение на 1).
    // Принимает id (идентификатор блюда).
    // suspend указывает, что метод является корутиной.
    suspend fun minusDish(id: Int): DishItem

    // suspend fun plusDish(id: Int): DishItem
    // Метод для увеличения количества блюда в корзине (увеличение на 1).
    // Принимает id (идентификатор блюда).
    // suspend указывает, что метод является корутиной.
    suspend fun plusDish(id: Int): DishItem //Прибавляет кол-во блюд в корзине +1
}