package com.example.mymenu.fastSearch.domain

import com.example.mymenu.menu.domain.DishRepository
import com.example.mymenu.coreModels.DishItem
import kotlinx.coroutines.flow.Flow

class GetSearchDishesUseCase(private val dishRepository: DishRepository) {
    suspend fun execute(query: String):  Flow<List<DishItem>>{
        return dishRepository.searchDishes(query)
    }
}