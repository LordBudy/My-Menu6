package com.example.mymenu.core.menumini.domain

import com.example.mymenu.core.models.DishItem

interface MenuMiniRepository {
    suspend fun getDish(dishId: Int, categoryId: Int): DishItem?

}