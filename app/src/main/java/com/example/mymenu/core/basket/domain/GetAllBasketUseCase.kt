package com.example.mymenu.core.basket.domain

import com.example.mymenu.core.models.DishItem
import kotlinx.coroutines.flow.Flow

class GetAllBasketUseCase(private val basketRepository: BasketRepository) {

    operator fun invoke(): Flow<List<DishItem>> {

        return basketRepository.getAllDishes()
    }
}