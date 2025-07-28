package com.example.mymenu.core.menumini.data

import com.example.mymenu.core.data.ApiService.DishDataSource
import com.example.mymenu.core.data.ModelsEntitys.DishEntity
import com.example.mymenu.core.models.DishItem
import com.example.mymenu.core.menumini.domain.MenuMiniRepository

class MenuMiniRepositoryImpl(private val dishDataSource: DishDataSource) : MenuMiniRepository {

    override suspend fun getDish(dishId: Int, categoryId: Int): DishItem? {
        // Получаем одно блюдо по dishId и categoryId
        val dishEntity = dishDataSource.getDish(dishId, categoryId)
        return dishEntity?.toDomainDishItem()
    }

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
        count = 1
    )