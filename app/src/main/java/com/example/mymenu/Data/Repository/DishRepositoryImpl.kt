package com.example.mymenu.Data.Repository

import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.ModelsEntitys.DishEntity
import com.example.mymenu.Domain.Menu.DishRepository
import com.example.mymenu.Domain.Models.DishItem

class DishRepositoryImpl(private val dishDataSource: DishDataSource
) : DishRepository {
    override suspend fun getDishs(categoryId: Int): List<DishItem> {
        val dishEntities = dishDataSource.getDishesByCategoryId(categoryId)
        return dishEntities.map { category ->
            category.toDomainDishItem()
        }
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

