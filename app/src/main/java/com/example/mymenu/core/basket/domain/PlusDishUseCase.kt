package com.example.mymenu.core.basket.domain

import com.example.mymenu.core.models.DishItem

class PlusDishUseCase(private val basketRepository: BasketRepository) {
    suspend operator fun invoke(id: Int): DishItem {
        return basketRepository.plusDish(id)
    }
}