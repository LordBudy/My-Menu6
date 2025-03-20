package com.example.mymenu.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.Domain.MenuMini.GetDishMiniUseCase
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.launch

class MenuMiniViewModel(

    // Внедряем UseCase через конструктор
    // getDishsUseCase - UseCase для получения списка блюд по ID категории
    private val getDishsUseCase: GetDishMiniUseCase,
    private val dishId: Int
) : ViewModel() {
    // _dishs - MutableLiveData для хранения списка блюд (приватная переменная)
    private val _dish = MutableLiveData<DishItem?>()

    // dish - LiveData для предоставления списка блюд UI (публичная переменная)
    // UI может только наблюдать за изменениями, но не изменять список напрямую
    val dish: LiveData<DishItem?> = _dish

    // Инициализатор - вызывается при создании экземпляра MenuViewModel
    init {
        // Загружаем блюда при создании ViewModel, передавая ID блюда
        loadDishs(dishId)
    }

    // loadDishs - метод для загрузки блюда
    // dishId -блюдо которое нужно добавить
    fun loadDishs(dishId: Int) {
        // viewModelScope - корутин скоуп, связанный с жизненным циклом ViewModel
        viewModelScope.launch {
            // Вызываем UseCase для получения списка блюд по ID категории
            val dish = getDishsUseCase.execute(dishId)
            _dish.value = dish
        }
    }
}