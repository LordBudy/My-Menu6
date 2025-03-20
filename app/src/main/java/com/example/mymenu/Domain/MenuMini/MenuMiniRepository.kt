package com.example.mymenu.Domain.MenuMini

import com.example.mymenu.Domain.Models.DishItem

interface MenuMiniRepository {
    suspend fun getdishMini(dishId: Int):DishItem?
}