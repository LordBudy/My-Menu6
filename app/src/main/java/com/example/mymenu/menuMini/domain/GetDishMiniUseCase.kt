package com.example.mymenu.menuMini.domain

import com.example.mymenu.coreModels.DishItem

class GetDishMiniUseCase(private val repositoryMini: MenuMiniRepository) {

    suspend fun execute(dishId: Int, categoryId: Int): DishItem? {

        return repositoryMini.getDish(dishId,categoryId)
    }
}