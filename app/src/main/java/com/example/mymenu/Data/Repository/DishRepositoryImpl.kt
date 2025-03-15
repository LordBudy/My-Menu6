package com.example.mymenu.Data.Repository

import android.view.MenuItem
import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.ModelsEntitys.DishEntity
import com.example.mymenu.Domain.Dish.DishRepository
import com.example.mymenu.Domain.Models.DishItem

class DishRepositoryImpl(private val dishDataSource: DishDataSource) : DishRepository {
    override suspend fun getDishs(id: Int): List<DishItem> {
        //гарантирует, что каждый список CategoryEntity в списке будет преобразован
        // в CategoryItem перед тем, как будет возвращен список
        return dishDataSource.getDishesByCategoryId(id).map{  dishEntity: DishEntity ->
            dishEntity.toDomainDishItem()
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
            categoryId = categoryId
        )

}