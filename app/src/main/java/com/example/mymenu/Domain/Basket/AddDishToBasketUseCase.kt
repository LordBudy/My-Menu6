package com.example.mymenu.Domain.Basket

import com.example.mymenu.Domain.Models.DishItem
// Объявляем класс AddDishToBasketUseCase (UseCase для добавления блюда в корзину)
// UseCase инкапсулирует бизнес-логику
class AddDishToBasketUseCase(private val basketRepository: BasketRepository) {

    // operator fun invoke() - позволяет вызывать этот класс как функцию, например: addDishToBasketUseCase(dishId)
    //функция принимает аргумент dishId типа Int (идентификатор блюда)
    // : DishItem - функция возвращает DishItem (добавленное блюдо)
    suspend operator fun invoke(dishId: Int): DishItem {
        // Вызываем метод addDishToBasket() из репозитория (basketRepository), передаем dishId
        // и возвращаем результат (DishItem)
        return basketRepository.addDishToBasket(dishId)
    }
}