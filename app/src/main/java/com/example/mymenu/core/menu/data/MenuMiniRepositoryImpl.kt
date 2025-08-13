package com.example.mymenu.core.menu.data

import com.example.mymenu.core.data.apiService.DishDataSource
import com.example.mymenu.core.data.modelsEntitys.BasketDishEntity
import com.example.mymenu.core.menu.domain.MenuMiniRepository
import com.example.mymenu.core.models.DishItem

class MenuMiniRepositoryImpl(private val dishDataSource: DishDataSource) : MenuMiniRepository {

    override suspend fun getDish(dishId: Int, categoryId: Int): DishItem? {
        // Получаем одно блюдо по dishId и categoryId
        val dishEntity = dishDataSource.getDish(dishId, categoryId)
        return dishEntity?.toDomainDishItem()
    }

}

private fun BasketDishEntity.toDomainDishItem(): DishItem =
    DishItem(
        id = id,
        url = url,
        name = name,
        price = price,
        weight = weight,
        description = description,
        categoryId = categoryId,
        count = 1
    )