package com.example.mymenu.Domain.MenuMini

import com.example.mymenu.Domain.Models.DishItem

class AddDishToBasketUseCase(private val repository: MenuMiniRepository) {

    suspend fun execute(dishId: Int): DishItem{

        return repository.addDishToBasket(dishId)
    }
}