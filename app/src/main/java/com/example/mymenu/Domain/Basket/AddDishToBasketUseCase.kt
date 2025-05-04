package com.example.mymenu.Domain.Basket

import com.example.mymenu.Domain.Models.DishItem

class AddDishToBasketUseCase(private val repository: BasketRepository) {

    suspend fun execute(dish: DishItem): DishItem {

        return repository.addDishToBasket(dish.id)
    }
}