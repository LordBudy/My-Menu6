package com.example.mymenu.Presentation.ViewModels.Interfaces

import com.example.mymenu.Domain.Models.DishItem

interface MiniInterface {

    fun showMini(dish: DishItem?)// отображение одного выбранного блюда fun navigateToBascket()//переход в корзину

    fun saveToBascket(dish: DishItem)// сохраняет выбранное блюдо в бд // сохраняет выбранное блюдо в бд
}