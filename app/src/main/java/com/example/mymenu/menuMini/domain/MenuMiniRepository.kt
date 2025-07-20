package com.example.mymenu.menuMini.domain

import com.example.mymenu.coreModels.DishItem

interface MenuMiniRepository {
    suspend fun getDish(dishId: Int, categoryId: Int): DishItem?

}