package com.example.mymenu.Domain.Dish

import com.example.mymenu.Domain.Models.DishItem

interface DishRepository {
    //выводит список блюд выбранной категории
    suspend fun getDishs(id: Int): List<DishItem>

}