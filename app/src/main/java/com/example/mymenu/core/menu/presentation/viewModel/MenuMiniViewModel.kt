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

    fun getDish(dishId: Int, categoryId: Int) {
        // viewModelScope - корутин скоуп, связанный с жизненным циклом ViewModel
        viewModelScope.launch {
            // Вызываем UseCase для получения списка блюд по ID категории
            try {
                _dish.value = getDishMiniUseCase.execute(dishId, categoryId)
            } catch (e: Exception) {
                Log.e("MenuMiniViewModel", "Ошибка при загрузке блюда", e)
            }
        }
    }
    fun addDishToBasket(dish: DishItem) {
        viewModelScope.launch {
            try {
                addDishToBasketUseCase.execute(dish.id)
                Log.d("MenuMiniViewModel", "Блюдо успешно добавлено: ${dish.name}")
            } catch (e: CancellationException) {
                Log.w("MenuMiniViewModel", "Добавление блюда отменено", e)
                throw e // важно пробросить, чтобы отмена корректно обработалась
            } catch (e: Exception) {
                Log.e("MenuMiniViewModel", "Ошибка добавления блюда", e)
            }
        }
    }
}