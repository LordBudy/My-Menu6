package com.example.mymenu.basket.domain

import com.example.mymenu.coreModels.DishItem
import kotlinx.coroutines.flow.Flow

class GetAllBasketUseCase(private val basketRepository: BasketRepository) {

    operator fun invoke(): Flow<List<DishItem>> {

        return basketRepository.getAllDishes()
    }
}