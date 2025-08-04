package com.example.mymenu.core.menu.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.core.basket.domain.AddDishToBasketUseCase
import com.example.mymenu.core.menu.domain.GetDishMiniUseCase
import com.example.mymenu.core.models.DishItem
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class MenuMiniViewModel(
    private val addDishToBasketUseCase: AddDishToBasketUseCase,
    private val getDishMiniUseCase: GetDishMiniUseCase,
) : ViewModel() {

    private val _dish = MutableLiveData<DishItem?>()
    val dish: LiveData<DishItem?> = _dish
    private var currentCategoryId: Int = -1

    fun getDish(dishId: Int, categoryId: Int) {
        currentCategoryId = categoryId
        // viewModelScope - корутин скоуп, связанный с жизненным циклом ViewModel
        viewModelScope.launch {
            // Вызываем UseCase для получения списка блюд по ID категории
            try {val dishItem = getDishMiniUseCase.execute(dishId, categoryId)
                //_dish.value = getDishMiniUseCase.execute(dishId, categoryId)
                Log.d("MenuMiniViewModel", "Получено блюдо: $dishItem")
                _dish.value = dishItem
            } catch (e: Exception) {
                Log.e("MenuMiniViewModel", "Ошибка при загрузке блюда", e)
            }
        }
    }

    fun addDishToBasket(dish: Int) {
        viewModelScope.launch {
            try {
                addDishToBasketUseCase.execute(dish)
                Log.d("MenuMiniViewModel", "Блюдо успешно добавлено: ${dish}")
            } catch (e: CancellationException) {
                Log.w("MenuMiniViewModel", "Добавление блюда отменено", e)
                throw e // важно пробросить, чтобы отмена корректно обработалась
            } catch (e: Exception) {
                Log.e("MenuMiniViewModel", "Ошибка добавления блюда", e)
            }
        }
    }
}