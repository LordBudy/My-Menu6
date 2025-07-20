package com.example.mymenu.basket.domain

class DeleteDishBasketUseCase(private val basketRepository: BasketRepository) {
    suspend operator fun invoke(dishId: Int) {
        basketRepository.deleteDishBasket(dishId)
    }
}