package com.example.mymenu.Data.Repository

import android.view.MenuItem
import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.ModelsEntitys.DishEntity
import com.example.mymenu.Domain.Dish.DishRepository
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.Flow

class DishRepositoryImpl(private val dishDataSource: DishDataSource
) : DishRepository {
    override fun getDishs(categoryId: Int): Flow<List<DishItem>> {
//  Получаем список DishEntity из DataSource
        val dishEntities = dishDataSource.getDishesByCategoryId(categoryId)
        //  Преобразуем список DishEntity в список DishItem и возвращаем Flow
        return kotlinx.coroutines.flow.flow {
            emit(dishEntities.map { it.toDomainDishItem() })
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

}