package com.example.mymenu.Data.Repository

import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.ModelsEntitys.DishEntity
import com.example.mymenu.Domain.MenuMini.MenuMiniRepository
import com.example.mymenu.Domain.Models.DishItem

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


