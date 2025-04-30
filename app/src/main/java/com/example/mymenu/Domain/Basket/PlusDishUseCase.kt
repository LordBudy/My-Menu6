package com.example.mymenu.Domain.Basket

import com.example.mymenu.Domain.Models.DishItem

class PlusDishUseCase(private val basketRepository: BasketRepository) {
    suspend operator fun invoke(id: Int): DishItem {
        return basketRepository.plusDish(id)
    }
}