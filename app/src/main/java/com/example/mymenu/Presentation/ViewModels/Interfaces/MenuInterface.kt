package com.example.mymenu.Presentation.ViewModels.Interfaces

import com.example.mymenu.Domain.Models.DishItem

interface MenuInterface {

    fun showMenu(dishList: List<DishItem>) //выводит список блюд

}