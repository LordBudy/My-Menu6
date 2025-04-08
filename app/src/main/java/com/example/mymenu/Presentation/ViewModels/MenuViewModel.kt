package com.example.mymenu.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.Domain.Dish.GetDishsUseCase
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.launch

// MenuViewModel - ViewModel для фрагмента Menu
class MenuViewModel(
    // Внедряем UseCase через конструктор
    // getDishsUseCase - UseCase для получения списка блюд по ID блюда и ID категории
    private val getDishsUseCase: GetDishsUseCase,
    private val categoryId: Int
) : ViewModel() {
    // _dishs - MutableLiveData для хранения списка блюд (приватная переменная)
    private val _dishs = MutableLiveData<List<DishItem>>()

    // dish - LiveData для предоставления списка блюд UI (публичная переменная)
    // UI может только наблюдать за изменениями, но не изменять список напрямую
    val dishs: LiveData<List<DishItem>> = _dishs

    // Инициализатор - вызывается при создании экземпляра MenuViewModel
    init {
        // Загружаем блюда при создании ViewModel, передавая ID категории
        loadDishes()
    }

    // loadDishes - метод для загрузки списка блюд
    // categoryId - ID категории, для которой нужно загрузить блюда
    fun loadDishes() {
        //  viewModelScope - корутин скоуп, связанный с жизненным циклом ViewModel
        viewModelScope.launch {
            //  Вызываем UseCase для получения списка блюд по ID категории
            getDishsUseCase(categoryId).collect { dishes -> //  Используем UseCase с categoryId
                //  Присваиваем полученный список блюд переменной _dishs
                _dishs.value = dishes
            }
        }
    }
}