package com.example.mymenu.Domain.MenuMini

import com.example.mymenu.Domain.Models.DishItem

class GetDishUseCase(private val repositoryMini: MenuMiniRepository) {

    suspend fun execute(dishId: Int, categoryId: Int): DishItem?{

        return repositoryMini.getDish(dishId, categoryId)
    }
}