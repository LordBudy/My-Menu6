package com.example.mymenu.Presentation.ViewModels

interface MenuMiniViewModelInterface {

    fun getDish(dishId: Int, categoryId: Int)
    fun addDishToBasket(dishId: Int)
}