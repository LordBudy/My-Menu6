package com.example.mymenu.core.menu.domain

import com.example.mymenu.core.models.DishItem
import kotlinx.coroutines.flow.Flow

interface DishRepository {
    //выводит список блюд выбранной категории
    suspend fun getDishs(categoryId: Int): List<DishItem>

    //выводит список блюд по названию
    suspend fun searchDishes(query: String): Flow<List<DishItem>>

}