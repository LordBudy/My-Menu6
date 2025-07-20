package com.example.mymenu.Data.Repository

import com.example.mymenu.coreData.ApiService.DishDataSource
import com.example.mymenu.coreData.ModelsEntitys.DishEntity
import com.example.mymenu.menu.domain.DishRepository
import com.example.mymenu.coreModels.DishItem
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

