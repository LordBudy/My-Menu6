package com.example.mymenu.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.Domain.MenuMini.GetDishUseCase
import com.example.mymenu.Domain.Models.DishItem
import kotlinx.coroutines.launch

class MenuMiniViewModel(

    // Внедряем UseCase через конструктор
    // getDishsUseCase - UseCase для получения блюда по ID категории
    private val getDishsUseCase: GetDishUseCase,
    private val dishId: Int
) : ViewModel(),MenuMiniViewModelInterface {


    // _dishs - MutableLiveData для хранения списка блюд (приватная переменная)
    private val _dish = MutableLiveData<DishItem?>()

    // dish - LiveData для предоставления списка блюд UI (публичная переменная)
    // UI может только наблюдать за изменениями, но не изменять список напрямую
    val dish: LiveData<DishItem?> = _dish

    // Инициализатор - вызывается при создании экземпляра MenuViewModel
    init {
        // Загружаем блюда при создании ViewModel, передавая ID блюда
        getDish(dishId)
    }
    // getDish - метод для загрузки блюда
    // dishId -блюдо которое нужно добавить
    override fun getDish(dishId: Int) {
        // viewModelScope - корутин скоуп, связанный с жизненным циклом ViewModel
        viewModelScope.launch {
            // Вызываем UseCase для получения списка блюд по ID категории
            val dish = getDishsUseCase.execute(dishId)
            _dish.value = dish
        }
    }

    override fun addDishToBasket(dishId: Int) {
        TODO("Not yet implemented")
    }
}