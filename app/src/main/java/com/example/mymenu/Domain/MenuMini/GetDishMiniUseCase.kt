package com.example.mymenu.Domain.MenuMini

import com.example.mymenu.Domain.Models.DishItem

class GetDishMiniUseCase(private val repositoryMini: MenuMiniRepository) {

    suspend fun execute(dishId: Int): DishItem?{

        return repositoryMini.getDish(dishId)
    }
}