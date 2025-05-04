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
class BasketRepositoryImpl(
    private val dishDataSource: DishDataSource,
    private val basketDao: BasketDao
) : BasketRepository {

    override suspend fun addDishToBasket(dish: Int): DishItem {
        val dishEntity = dishDataSource.getDish(dish, categoryId = 1)
            ?: throw IllegalArgumentException("Блюдо с ID $dish не найдено")
        basketDao.insertDish(dishEntity)
        return dishEntity.toDomainDishItem()
    }
    override fun getAllDishes(): Flow<List<DishItem>> =
        basketDao.getAllDishs().map { list ->
            Log.d("BasketRepositoryImpl", "getAllDishes: list size = ${list.size}")
            list.map { it.toDomainDishItem() }
        }
    override suspend fun deleteDishBasket(dishId: Int) {
        basketDao.deleteDishById(dishId)  //  Передаем dishId
    }
    override suspend fun minusDish(id: Int): DishItem {
        // Получаем блюдо по ID из базы данных
        val dishEntity = basketDao.getDishById(id) ?: throw IllegalArgumentException("Блюдо с ID $id не найдено")

        // Уменьшаем количество, но только если оно больше 1
        if (dishEntity.count > 1) {
            dishEntity.count -= 1
            basketDao.updateDish(dishEntity)
        } else if (dishEntity.count == 1) {
            // Если количество равно 1, можно удалить блюдо из корзины
            deleteDishBasket(id)
            return dishEntity.toDomainDishItem() // Возвращаем DishItem, если удалили
        }
        // Возвращаем преобразованное блюдо
        return dishEntity.toDomainDishItem()
    }
    override suspend fun plusDish(id: Int): DishItem {
        // Получаем блюдо по ID из базы данных
        val dishEntity = basketDao.getDishById(id) ?: throw IllegalArgumentException("Блюдо с ID $id не найдено")
        // Увеличиваем количество блюда
        dishEntity.count += 1
        // Обновляем блюдо в базе данных
        basketDao.updateDish(dishEntity)
        // Возвращаем преобразованное блюдо
        return dishEntity.toDomainDishItem()
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
