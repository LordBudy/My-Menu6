package com.example.mymenu.core.basket.domain

import com.example.mymenu.core.models.DishItem

class AddDishToBasketUseCase(private val repository: BasketRepository) {

    suspend fun execute(dish: DishItem): DishItem {

        return repository.addDishToBasket(dish.id)
    }
}