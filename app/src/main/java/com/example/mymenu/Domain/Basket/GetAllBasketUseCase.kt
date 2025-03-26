package com.example.mymenu.Domain.Basket

import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.Flow
// Объявляем класс GetAllBasketUseCase (UseCase для получения всех элементов корзины)
// UseCase инкапсулирует бизнес-логику
class GetAllBasketUseCase(private val basketRepository: BasketRepository) {
// suspend operator fun invoke(): Flow<List<DishItem>>
    // operator fun invoke() - позволяет вызывать этот класс как функцию, например: getAllBasketUseCase()
    // suspend - указывает, что функция является корутиной (может быть приостановлена и возобновлена)
    // (): Flow<List<DishItem>> - функция не принимает аргументов и возвращает Flow, содержащий список DishItem
    suspend operator fun invoke(): Flow<List<DishItem>> {
        // Вызываем метод getAllDishes() из репозитория (basketRepository)
        // и возвращаем результат (Flow<List<DishItem>>)
        return basketRepository.getAllDishes()
    }
}