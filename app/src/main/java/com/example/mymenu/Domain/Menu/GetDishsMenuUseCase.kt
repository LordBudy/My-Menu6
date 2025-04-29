package com.example.mymenu.Domain.Menu

import com.example.mymenu.Domain.Models.DishItem

class GetDishsMenuUseCase(private val dishRepository: DishRepository) {
     suspend fun execute(categoryId: Int):  List<DishItem>{
        return dishRepository.getDishs(categoryId)
    }
}