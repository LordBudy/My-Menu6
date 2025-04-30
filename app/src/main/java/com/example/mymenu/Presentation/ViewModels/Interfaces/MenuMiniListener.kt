package com.example.mymenu.Presentation.ViewModels.Interfaces

import com.example.mymenu.Domain.Models.DishItem

interface MenuMiniListener {
    fun onAddToCartClicked(dishItem: DishItem)
}