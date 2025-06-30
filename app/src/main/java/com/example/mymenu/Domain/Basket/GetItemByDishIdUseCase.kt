package com.example.mymenu.Domain.Basket

class GetItemByDishIdUseCase(private val basketRepository: BasketRepository) {

    suspend operator fun invoke(dishId: Int) {
        basketRepository.getBasketItemByDishId(dishId)
    }
}