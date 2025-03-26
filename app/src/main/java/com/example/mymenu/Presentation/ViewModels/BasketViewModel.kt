package com.example.mymenu.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.Domain.Basket.GetAllBasketUseCase
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Объявляем класс BasketViewModel, который наследуется от ViewModel (предоставляет данные для UI)
class BasketViewModel(private val getAllBasketUseCase: GetAllBasketUseCase): ViewModel() {
    // _basketItems - внутренний, приватный MutableLiveData для хранения списка элементов корзины
    // MutableLiveData позволяет изменять список блюд
    private val _basketItems = MutableLiveData<List<DishItem>>()
    // basketItems - публичный LiveData, который предоставляет доступ к списку блюд только для чтения
    // LiveData автоматически обновляет UI при изменении данных
    val basketItems: LiveData<List<DishItem>> = _basketItems

    // Блок инициализации (выполняется при создании ViewModel)
    init {
        loadBasketItems() // Загружаем элементы корзины при создании ViewModel
    }

    // Метод для загрузки элементов корзины
    private fun loadBasketItems() {
        // Запускаем корутину в viewModelScope (scope жизненного цикла ViewModel)
        viewModelScope.launch {
            // Вызываем UseCase для получения данных корзины (getAllBasketUseCase().collect)
            // UseCase, в свою очередь, обращается к репозиторию и получает данные из БД
            // collect - это функция-расширение, которая собирает данные из потока (Flow)
            // Получаем данные из Flow
            getAllBasketUseCase().collect { items ->
                // Обновляем значение _basketItems (MutableLiveData) новым списком блюд
                // Обновляем данные в LiveData (это вызовет обновление UI, если есть наблюдатели)
                _basketItems.value = items
            }
        }
    }
}