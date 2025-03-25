package com.example.mymenu.Domain.Basket

import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.Flow

class GetAllBasketUseCase(private val basketRepository: BasketRepository) {

    suspend operator fun invoke(): Flow<List<DishItem>> {
        return basketRepository.getAllDishes()
    }
}