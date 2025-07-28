package com.example.mymenu.core.basket.domain

class GetItemByDishIdUseCase(private val basketRepository: BasketRepository) {

    suspend operator fun invoke(dishId: Int) {
        basketRepository.getBasketItemByDishId(dishId)
    }
}