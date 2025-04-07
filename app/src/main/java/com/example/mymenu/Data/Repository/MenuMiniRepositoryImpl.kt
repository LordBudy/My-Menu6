package com.example.mymenu.Data.Repository

import com.example.mymenu.Data.ApiService.DishDataSource
import com.example.mymenu.Data.ModelsEntitys.DishEntity
import com.example.mymenu.Domain.MenuMini.MenuMiniRepository
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MenuMiniRepositoryImpl(private val dishDataSource: DishDataSource): MenuMiniRepository {

    override fun getDish(id: Int, categoryId: Int): DishItem? {
        TODO("Not yet implemented")
    }

    override suspend fun saveDishToDB(dishId: Int, categoryId: Int): DishItem {
        TODO("Not yet implemented")
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