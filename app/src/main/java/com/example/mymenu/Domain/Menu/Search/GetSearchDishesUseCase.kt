package com.example.mymenu.Domain.Menu.Search

import com.example.mymenu.Domain.Menu.DishRepository
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.Flow

class GetSearchDishesUseCase(private val dishRepository: DishRepository) {
    suspend fun execute(query: String):  Flow<List<DishItem>>{
        return dishRepository.searchDishes(query)
    }
}