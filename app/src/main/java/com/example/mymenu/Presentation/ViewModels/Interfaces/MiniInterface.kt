package com.example.mymenu.Presentation.ViewModels.Interfaces

import com.example.mymenu.Domain.Models.DishItem

interface MiniInterface {

    fun showMini(dish: DishItem?) // отображение одного выбранного блюда
    fun navigateToBascket() //переход в корзину
    fun saveToBascket(dishId: DishItem) // сохраняет выбранное блюдо в бд
}