package com.example.mymenu.Data.Repository

import android.util.Log
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

    override suspend fun addDishToBasket(dishId: Int): DishItem {
        val dishEntity = dishDataSource.getDish(dishId, categoryId = 1)
            ?: throw IllegalArgumentException("Dish with ID $dishId not found")

        basketDao.insertDish(dishEntity)
        return dishEntity.toDomainDishItem()
    }
    override fun getAllDishes(): Flow<List<DishItem>> =
        basketDao.getAllDishs().map { list ->
            Log.d("BasketRepositoryImpl", "getAllDishes: list size = ${list.size}")
            list.map { it.toDomainDishItem() }
        }

    override suspend fun deleteDishBasket(dish: DishItem) {
        val dishEntity = DishEntity(
            id = dish.id,
            url = dish.url,
            name = dish.name,
            price = dish.price,
            weight = dish.weight,
            description = dish.description,
            categoryId = dish.categoryId,
            count = dish.count
        )
        basketDao.deleteBasketItem(dishEntity)
    }

    override suspend fun minusDish(id: Int): DishItem {
        TODO()
    }

    override suspend fun plusDish(id: Int): DishItem {
        TODO()
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
