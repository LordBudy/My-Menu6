package com.example.mymenu.core.basket.domain

import com.example.mymenu.core.models.DishItem

class UpdateBasketUseCase(private val basketRepository: BasketRepository) {

    suspend fun invoke(basketItem: DishItem){
        return basketRepository.updateBasketItem(basketItem)
    }
}