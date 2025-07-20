package com.example.mymenu.basket.domain

import com.example.mymenu.coreModels.DishItem

class UpdateBasketUseCase(private val basketRepository: BasketRepository) {

    suspend fun invoke(basketItem: DishItem){
        return basketRepository.updateBasketItem(basketItem)
    }
}