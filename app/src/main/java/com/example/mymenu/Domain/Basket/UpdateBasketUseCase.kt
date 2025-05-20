package com.example.mymenu.Domain.Basket

import com.example.mymenu.Domain.Models.DishItem

class UpdateBasketUseCase(private val basketRepository: BasketRepository) {

    suspend fun invoke(basketItem: DishItem){
        return basketRepository.updateBasketItem(basketItem)
    }
}