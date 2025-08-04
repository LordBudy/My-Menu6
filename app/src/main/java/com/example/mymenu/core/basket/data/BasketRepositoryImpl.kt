package com.example.mymenu.core.basket.data

import android.util.Log
import com.example.mymenu.core.basket.domain.BasketRepository
import com.example.mymenu.core.data.ApiService.DishDataSource
import com.example.mymenu.core.data.DAO.BasketDao
import com.example.mymenu.core.data.ModelsEntitys.DishEntity
import com.example.mymenu.core.models.DishItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BasketRepositoryImpl(
    private val dishDataSource: DishDataSource,
    private val basketDao: BasketDao
) : BasketRepository {

    override suspend fun addDishToBasket(dish: Int): DishItem {
        Log.d("BasketRepo", "addDishToBasket вызывается с dishId: $dish")
        val dishEntity = basketDao.getDishById(dish)
        Log.d("BasketRepo", "existingDishEntity: $dishEntity")
        return if (dishEntity != null) {
            dishEntity.count += 1
            basketDao.updateDish(dishEntity)
            dishEntity.toDomainDishItem()
        } else {
            val dishItem = dishDataSource.getDishById(dish)
                ?: throw Exception("id блюда не найден")
            val newDishEntity = DishEntity(
                id = dishItem.id,
                url = dishItem.url,
                name = dishItem.name,
                price = dishItem.price,
                weight = dishItem.weight,
                description = dishItem.description,
                categoryId = dishItem.categoryId,
                count = 1
            )
            basketDao.insertDish(newDishEntity)
            Log.d("BasketRepo", "новый объект DishEntity: $newDishEntity")
            newDishEntity.toDomainDishItem()
        }

    }

    override fun getAllDishes(): Flow<List<DishItem>> =
        basketDao.getAllDishs().map { list ->
            Log.d("BasketRepositoryImpl", "getAllDishes: размер списка = ${list.size}")
            list.map { it.toDomainDishItem() }
        }

    override suspend fun getBasketItemByDishId(dishId: Int): DishItem? {
        return basketDao.getDishById(dishId)?.toDomainDishItem()
    }

    override suspend fun updateBasketItem(basketItem: DishItem) {
        val dishEntity = basketItem.toDishEntity()
        basketDao.updateDish(dishEntity)
    }

    override suspend fun deleteDishBasket(dishId: Int) {
        basketDao.deleteDishById(dishId)
    }

    override suspend fun minusDish(id: Int): DishItem? {
        // Получаем блюдо по ID из базы данных
        val dishEntity = basketDao.getDishById(id) ?:
        throw IllegalArgumentException("Блюдо с ID $id не найдено")
        // Уменьшаем количество, но только если оно больше 1
        return if (dishEntity.count > 1) {
            dishEntity.count -= 1
            basketDao.updateDish(dishEntity)
            dishEntity.toDomainDishItem()
        } else {
            basketDao.deleteDishById(id)
            null
        }
    }
    override suspend fun plusDish(id: Int): DishItem {
        // Получаем блюдо по ID из базы данных
        val dishEntity = basketDao.getDishById(id) ?:
        throw IllegalArgumentException("Блюдо с ID $id не найдено")
        // Увеличиваем количество блюда
        dishEntity.count += 1
        // Обновляем блюдо в базе данных
        basketDao.updateDish(dishEntity)
        // Возвращаем преобразованное блюдо
        return dishEntity.toDomainDishItem()
    }



    // преобразование DishEntity в DishItem
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

    // преобразование DishItem в DishEntity
    private fun DishItem.toDishEntity(): DishEntity =
        DishEntity(
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