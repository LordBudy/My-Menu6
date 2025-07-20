package com.example.mymenu.Data.Repository

import com.example.mymenu.coreData.ApiService.DishDataSource
import com.example.mymenu.coreData.ModelsEntitys.DishEntity
import com.example.mymenu.menuMini.domain.MenuMiniRepository
import com.example.mymenu.coreModels.DishItem

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


