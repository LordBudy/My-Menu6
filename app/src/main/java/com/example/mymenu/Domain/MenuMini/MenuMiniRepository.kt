package com.example.mymenu.Domain.MenuMini

import com.example.mymenu.Domain.Models.DishItem

interface MenuMiniRepository {
    fun getDish(id: Int, categoryId: Int):DishItem?
    suspend fun saveDishToDB(dishId: Int, categoryId: Int):DishItem
}