package com.example.mymenu.Domain.Basket

import com.example.mymenu.Domain.Models.DishItem

class AddDishToBasketUseCase(private val basketRepository: BasketRepository) {

    suspend operator fun invoke(dishId: Int): DishItem {
        return basketRepository.addDishToBasket(dishId)
    }
}