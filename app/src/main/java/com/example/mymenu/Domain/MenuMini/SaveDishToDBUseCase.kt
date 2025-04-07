package com.example.mymenu.Domain.MenuMini

import com.example.mymenu.Domain.Models.DishItem

class SaveDishToDBUseCase(private val repository: MenuMiniRepository) {

    suspend fun execute(dishId: Int, categoryId: Int): DishItem{

        return repository.saveDishToDB(dishId, categoryId)
    }
}