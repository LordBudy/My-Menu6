package com.example.mymenu.Domain.Dish

import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.Flow

interface DishRepository {
    //выводит список блюд выбранной категории
    fun getDishs(dishId: Int, categoryId: Int): List<DishItem>

}