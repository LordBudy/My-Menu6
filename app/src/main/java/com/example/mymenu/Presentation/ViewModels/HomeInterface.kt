package com.example.mymenu.Presentation.ViewModels

import com.example.mymenu.Domain.Models.CategoryItem


interface HomeInterface {

    fun showCategoryes(categoryes: List<CategoryItem>) //  Показать список блюд
    fun navigateToMenu(categoryId: CategoryItem) //  Перейти к деталям блюда
}