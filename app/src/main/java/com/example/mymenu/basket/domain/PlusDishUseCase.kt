package com.example.mymenu.basket.domain

import com.example.mymenu.coreModels.DishItem

class PlusDishUseCase(private val basketRepository: BasketRepository) {
    suspend operator fun invoke(id: Int): DishItem {
        return basketRepository.plusDish(id)
    }
}