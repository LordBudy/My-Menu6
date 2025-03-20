package com.example.mymenu.Data.Repository

import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.ModelsEntitys.DishEntity
import com.example.mymenu.Domain.MenuMini.MenuMiniRepository
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MenuMiniRepositoryImpl(private val dishDataSource: DishDataSource): MenuMiniRepository {
    override suspend fun getdishMini(dishId: Int): DishItem? = withContext(Dispatchers.IO){
        //гарантирует, что каждый список DishEntity в списке будет преобразован
        // в DishItem перед тем, как будет возвращен список
        dishDataSource.getDishById(dishId)?.toDomainDishItem()

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