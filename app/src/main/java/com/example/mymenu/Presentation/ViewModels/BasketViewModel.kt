package com.example.mymenu.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.Domain.Basket.GetAllBasketUseCase
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.Presentation.ViewModels.Interfaces.MenuInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// Объявляем класс BasketViewModel, который наследуется от ViewModel (предоставляет данные для UI)
class BasketViewModel(
    private val getAllBasketUseCase: GetAllBasketUseCase
) : ViewModel() {

    private val _basketItems = MutableLiveData<List<DishItem>>()
    val basketItems: LiveData<List<DishItem>> = _basketItems

    // Блок инициализации (выполняется при создании ViewModel)
    init {
        loadBasketItems() // Загружаем элементы корзины при создании ViewModel
    }

    // Метод для загрузки элементов корзины
    fun loadBasketItems() {
        // Запускаем корутину в viewModelScope (scope жизненного цикла ViewModel)
        viewModelScope.launch {
            getAllBasketUseCase().collectLatest { items ->
                _basketItems.value = items
            }
        }
    }
}