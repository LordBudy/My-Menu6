package com.example.mymenu.Domain.Basket

class DeleteDishBasketUseCase(private val basketRepository: BasketRepository) {
    suspend operator fun invoke(dishId: Int) {
        basketRepository.deleteDishBasket(dishId)
    }
}