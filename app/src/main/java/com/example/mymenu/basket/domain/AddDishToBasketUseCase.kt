package com.example.mymenu.basket.domain

import com.example.mymenu.coreModels.DishItem

class AddDishToBasketUseCase(private val repository: BasketRepository) {

    suspend fun execute(dish: DishItem): DishItem {

        return repository.addDishToBasket(dish.id)
    }
}