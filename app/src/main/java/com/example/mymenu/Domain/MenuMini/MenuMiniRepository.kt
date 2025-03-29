package com.example.mymenu.Domain.MenuMini

import com.example.mymenu.Domain.Models.DishItem

interface MenuMiniRepository {
    suspend fun getDish(dishId: Int):DishItem?
    suspend fun addDishToBasket(dishId: Int):DishItem
}