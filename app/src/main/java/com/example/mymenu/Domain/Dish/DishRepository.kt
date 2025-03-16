package com.example.mymenu.Domain.Dish

import com.example.mymenu.Domain.Models.DishItem

// Интерфейс DishRepository (Определяет методы для работы с данными о блюдах)
interface DishRepository {
    suspend fun getDishs(id: Int): List<DishItem>
}