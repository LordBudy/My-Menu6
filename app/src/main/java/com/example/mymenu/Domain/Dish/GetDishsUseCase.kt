package com.example.mymenu.Domain.Dish

import com.example.mymenu.Domain.Models.DishItem

class GetDishsUseCase(private val dishRepository: DishRepository) {
    suspend fun execute(categoryId: Int): List<DishItem> {
        return dishRepository.getDishs(categoryId)
    }
}