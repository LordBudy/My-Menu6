package com.example.mymenu.Domain.Basket

import com.example.mymenu.Domain.MenuMini.MenuMiniRepository
import com.example.mymenu.Domain.Models.DishItem

class AddDishToBasketUseCase(private val repository: BasketRepository) {

    suspend fun execute(dishId: Int): DishItem?{

        return repository.addDishToBasket(dishId)
    }
}