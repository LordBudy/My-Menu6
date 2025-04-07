package com.example.mymenu.Data.Repository

import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.DAO.BasketDao
import com.example.mymenu.Data.ModelsEntitys.DishEntity
import com.example.mymenu.Domain.Basket.BasketRepository
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
// Этот класс отвечает за доступ к данным корзины
class BasketRepositoryImpl(
    // Источник данных для получения информации о блюдах
    private val dishDataSource: DishDataSource,
    // DAO для работы с базой данных корзины
    private val basketDao: BasketDao
    // Указываем, что этот класс реализует интерфейс BasketRepository
) : BasketRepository {
    // Метод для добавления блюда в корзину. Принимает dishId (идентификатор блюда)
    // и возвращает добавленное блюдо (DishItem).
    // suspend указывает, что метод является корутиной (может быть приостановлен и возобновлен).
    override suspend fun addDishToBasket(dishId: Int): DishItem {
        TODO("Not yet implemented")
    }
    // Метод для получения всех блюд. Возвращает Flow, который содержит список DishItem.
    // Flow позволяет получать данные асинхронно и реактивно (автоматически получать обновления данных).
    override fun getAllDishes(): Flow<List<DishItem>> {

        TODO("Not yet implemented")
    }
    override suspend fun deleteDishBasket(dish: DishItem) {
        TODO("Not yet implemented")
    }

    override suspend fun minusDish(id: Int): DishItem {
        TODO("Not yet implemented")
    }

    override suspend fun plusDish(id: Int): DishItem {
        TODO("Not yet implemented")
    }
    // Функция-расширение для преобразования DishEntity в DishItem.
    private fun DishEntity.toDomainDishItem(): DishItem =
        DishItem(
            id = id,
            url = url,
            name = name,
            price = price,
            weight = weight,
            description = description,
            categoryId = categoryId,
            count = count
        )
}
