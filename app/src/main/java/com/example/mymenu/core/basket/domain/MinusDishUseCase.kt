package com.example.mymenu.core.basket.domain

import com.example.mymenu.core.models.DishItem

class MinusDishUseCase(private val basketRepository: BasketRepository) {
    suspend operator fun invoke(id: Int): DishItem? {
        return basketRepository.minusDish(id)
    }
}