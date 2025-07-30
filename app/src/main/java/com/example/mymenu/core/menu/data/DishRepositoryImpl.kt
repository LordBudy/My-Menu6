package com.example.mymenu.core.menu.data

import android.util.Log
import com.example.mymenu.core.data.ApiService.DishDataSource
import com.example.mymenu.core.data.ModelsEntitys.DishEntity
import com.example.mymenu.core.models.DishItem
import com.example.mymenu.core.menu.domain.DishRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class DishRepositoryImpl(
    private val dishDataSource: DishDataSource
) : DishRepository {
    override suspend fun getDishs(categoryId: Int): List<DishItem> {
        val dishEntities = dishDataSource.getDishesByCategoryId(categoryId)
        return dishEntities.map { category ->
            category.toDomainDishItem()
        }
    }

    override fun searchDishes(query: String): Flow<List<DishItem>> = flow { // Изменили тип возвращаемого значения
        Log.d("DishRepositoryImpl", "Поиск блюд по запросу: '$query'")
        val dishItems = mutableListOf<DishItem>() // Создаем изменяемый список для накопления
        dishDataSource.getDishAcrossAllCategoriesByName(query) // Получаем Flow<DishEntity>
            .map { dishEntity ->
                val dishItem = dishEntity.toDomainDishItem()
                Log.d("DishRepositoryImpl", "Найденно блюдо: ${dishItem.name} (ID: ${dishItem.id})") // Добавляем лог для каждого найденного блюда
                dishItem
            }
            .collect { dishItem ->
                dishItems.add(dishItem)
            }
        emit(dishItems)
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