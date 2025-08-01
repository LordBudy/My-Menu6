package com.example.mymenu.core.basket.domain

import com.example.mymenu.core.models.DishItem

class AddDishToBasketUseCase(private val basketRepository: BasketRepository) {

    suspend fun execute(dishItem: Int): DishItem   {

        return basketRepository.addDishToBasket(dishItem)
    }
}