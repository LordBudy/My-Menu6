package com.example.mymenu.Presentation.ViewModels.Interfaces

import com.example.mymenu.Domain.Models.CategoryItem


interface HomeInterface {

    fun showCategoryes(categoryes: List<CategoryItem>) //  Показать список блюд
    fun onCategoryClicked(categoryId: CategoryItem) //  Перейти в меню выбранной категории
}