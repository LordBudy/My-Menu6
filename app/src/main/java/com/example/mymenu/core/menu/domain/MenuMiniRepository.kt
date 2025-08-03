package com.example.mymenu.core.menu.domain

import com.example.mymenu.core.models.DishItem

interface MenuMiniRepository {
    suspend fun getDish(dishId: Int, categoryId: Int): DishItem?

}