package com.example.mymenu.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.Data.ModelsEntitys.DishEntity
import com.example.mymenu.Domain.Basket.AddDishToBasketUseCase
import com.example.mymenu.Domain.MenuMini.GetDishMiniUseCase
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.Presentation.ViewModels.Interfaces.MiniInterface
import kotlinx.coroutines.launch

class MenuMiniViewModel(
    private val addDishToBasketUseCase: AddDishToBasketUseCase,
    private val getDishMiniUseCase: GetDishMiniUseCase,
    private val miniInterface: MiniInterface
) : ViewModel() {
    // _dishs - MutableLiveData для хранения списка блюд (приватная переменная)
    private val _dish = MutableLiveData<DishItem?>()

    // dish - LiveData для предоставления списка блюд UI (публичная переменная)
    val dish: LiveData<DishItem?> = _dish

    // getDish - метод для загрузки блюда
    fun getDish(dishId: Int, categoryId: Int) {
        // viewModelScope - корутин скоуп, связанный с жизненным циклом ViewModel
        viewModelScope.launch {
            // Вызываем UseCase для получения списка блюд по ID категории
            try {
                val dish = getDishMiniUseCase.execute(dishId, categoryId)
                _dish.value = dish
                miniInterface.showMini(dish)
            } catch (e: Exception) {
                println("Error fetching dish: ${e.message}")
                _dish.value = null
                miniInterface.showMini(null)
            }
        }
    }
}