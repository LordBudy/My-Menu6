package com.example.mymenu.core.menumini.domain

import com.example.mymenu.core.models.DishItem

class GetDishMiniUseCase(private val repositoryMini: MenuMiniRepository) {

    suspend fun execute(dishId: Int, categoryId: Int): DishItem? {

        return repositoryMini.getDish(dishId,categoryId)
    }
}