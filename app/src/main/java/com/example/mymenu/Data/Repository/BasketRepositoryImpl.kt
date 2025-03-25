package com.example.mymenu.Data.Repository

import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.DAO.BasketDao
import com.example.mymenu.Data.ModelsEntitys.BasketItemEntity
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

    override suspend fun addDishToBasket(dishId: Int): DishItem = withContext(Dispatchers.IO) {
        val dishEntity = dishDataSource.getDishById(dishId)
            ?: throw IllegalArgumentException("Dish with id $dishId not found")

        val basketItemEntity = BasketItemEntity(
            id = dishEntity.id,
            name = dishEntity.name,
            price = dishEntity.price,
            weight = dishEntity.weight,
            count = 1 // Default quantity
        )
        basketDao.insertBasketItem(basketItemEntity)
        dishEntity.toDomainDishItem() // Convert and return DishItem
    }

    override fun getAllDishes(): Flow<List<DishItem>> {
        return basketDao.getAllDishes().map { list ->
            list.map { it.toDomainDishItem() }
        }
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

    private fun BasketItemEntity.toDomainDishItem(): DishItem =
        DishItem(
            id = id,
            url = "", // Replace with actual url if you store it in BasketItem
            name = name,
            price = price,
            weight = weight,
            description = "",
            categoryId = null,
            count = count
        )
}
