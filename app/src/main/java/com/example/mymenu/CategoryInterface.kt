package com.example.mymenu

import com.example.mymenu.coreModels.CategoryItem


interface CategoryInterface {

    fun showCategoryes(categoryes: List<CategoryItem>) //  Показать список блюд
    fun onCategoryClicked(categoryId: CategoryItem) //  Перейти в меню выбранной категории
}