package com.example.mymenu.core.menu.data

import com.example.mymenu.core.data.ApiService.DishDataSource
import com.example.mymenu.core.data.ModelsEntitys.DishEntity
import com.example.mymenu.core.models.DishItem
import com.example.mymenu.core.menu.domain.DishRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DishRepositoryImpl(private val dishDataSource: DishDataSource
) : DishRepository {
    override suspend fun getDishs(categoryId: Int): List<DishItem> {
        val dishEntities = dishDataSource.getDishesByCategoryId(categoryId)
        return dishEntities.map { category ->
            category.toDomainDishItem()
        }
    }

    override suspend fun searchDishes(query: String): Flow<List<DishItem>> {
        return flow {
            val dishFlow = dishDataSource.getDishAcrossAllCategoriesByName(query)
            val dishList = mutableListOf<DishItem>()

            dishFlow.collect { dishEntity ->
                dishList.add(dishEntity.toDomainDishItem())
            }

            emit(dishList)
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