package com.example.mymenu.core.menu.domain

import com.example.mymenu.core.models.DishItem

class GetDishsMenuUseCase(private val dishRepository: DishRepository) {
     suspend fun execute(categoryId: Int):  List<DishItem>{
        return dishRepository.getDishs(categoryId)
    }
}