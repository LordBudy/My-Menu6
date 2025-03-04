package com.example.mymenu.Domain.Category1

import com.example.mymenu.Domain.Models.DishItem

class OpenGetMenuUseCase(private val categoryRepository: CategoryListRepository) {

    suspend fun OpenGetMenu():List<DishItem>{
        return categoryRepository.OpenGetMenuItems()
    }
}