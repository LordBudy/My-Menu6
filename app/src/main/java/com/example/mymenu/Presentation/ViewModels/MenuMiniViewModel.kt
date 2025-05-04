package com.example.mymenu.Presentation.ViewModels

import android.util.Log
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
) : ViewModel() {

    private val _dish = MutableLiveData<DishItem?>()
    val dish: LiveData<DishItem?> = _dish

    fun getDish(dishId: Int, categoryId: Int) {
        // viewModelScope - корутин скоуп, связанный с жизненным циклом ViewModel
        viewModelScope.launch {
            // Вызываем UseCase для получения списка блюд по ID категории
            try {
                _dish.value = getDishMiniUseCase.execute(dishId, categoryId)
            } catch (e: Exception) {
                println("Error fetching dish: ${e.message}")
                _dish.value = null
            }
        }
    }
    fun addDishToBasket(dish: DishItem) {
        viewModelScope.launch {
            try {
                addDishToBasketUseCase.execute(dish)
            } catch (e: Exception) {
                Log.d("MiniViewModel", "ошибка добавления влюда в бд")
                // Обработка ошибок (например, логирование)
            }
        }
    }
}