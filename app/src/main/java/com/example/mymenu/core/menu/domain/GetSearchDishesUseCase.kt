package com.example.mymenu.core.menu.domain

import com.example.mymenu.core.models.DishItem
import kotlinx.coroutines.flow.Flow

class GetSearchDishesUseCase(private val dishRepository: DishRepository) {
    fun execute(query: String): Flow<List<DishItem>> {
        return dishRepository.searchDishes(query)
    }
}