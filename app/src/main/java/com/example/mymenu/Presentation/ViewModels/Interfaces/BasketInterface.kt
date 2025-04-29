package com.example.mymenu.Presentation.ViewModels.Interfaces

import com.example.mymenu.Domain.Models.DishItem

interface BasketInterface {
    // отображение всех блюд в корзине (в корзине и в базе данных должно быть одинаково т.к.
    // в корзину они загружаются при любом его открытии)
    fun showAllBasket(dishs: DishItem?)
    fun plus(dishId: DishItem) // убавляет count - 1 и сохраняет измененное значение в базу данных
    fun minus(dishId: DishItem) // прибавляет count + 1 сохраняет измененное значение в базу данных
    fun delete(dishId: DishItem) // удаляет блюдо из бд и из корзины если count < 1

}