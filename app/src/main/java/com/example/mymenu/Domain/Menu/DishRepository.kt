package com.example.mymenu.Domain.Menu

import com.example.mymenu.Domain.Models.DishItem

interface DishRepository {
    //выводит список блюд выбранной категории
    suspend fun getDishs(categoryId: Int):  List<DishItem>

}