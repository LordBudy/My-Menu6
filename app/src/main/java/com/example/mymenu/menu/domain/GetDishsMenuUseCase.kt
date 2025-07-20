package com.example.mymenu.menu.domain

import com.example.mymenu.coreModels.DishItem

class GetDishsMenuUseCase(private val dishRepository: DishRepository) {
     suspend fun execute(categoryId: Int):  List<DishItem>{
        return dishRepository.getDishs(categoryId)
    }
}