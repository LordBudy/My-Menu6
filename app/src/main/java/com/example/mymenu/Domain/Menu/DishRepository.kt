package com.example.mymenu.Domain.Menu

import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.Flow

interface DishRepository {
    //выводит список блюд выбранной категории
    suspend fun getDishs(categoryId: Int): List<DishItem>

    //выводит список блюд по названию
    suspend fun searchDishes(query: String): Flow<List<DishItem>>

}