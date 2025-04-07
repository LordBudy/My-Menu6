package com.example.mymenu.Domain.Dish

import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.Flow

class GetDishsUseCase(private val dishRepository: DishRepository) {
    operator fun invoke(dishId: Int, categoryId: Int): List<DishItem>{
        return dishRepository.getDishs(dishId, categoryId)
    }
}