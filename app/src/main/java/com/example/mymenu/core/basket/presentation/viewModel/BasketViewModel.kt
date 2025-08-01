package com.example.mymenu.core.basket.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.core.basket.domain.AddDishToBasketUseCase
import com.example.mymenu.core.basket.domain.DeleteDishBasketUseCase
import com.example.mymenu.core.basket.domain.GetAllBasketUseCase
import com.example.mymenu.core.basket.domain.MinusDishUseCase
import com.example.mymenu.core.basket.domain.PlusDishUseCase
import com.example.mymenu.core.models.DishItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BasketViewModel(
    private val addDishToBasketUseCase: AddDishToBasketUseCase,
    private val getAllBasketUseCase: GetAllBasketUseCase,
    private val deleteDishBasketUseCase: DeleteDishBasketUseCase,
    private val minusDishUseCase: MinusDishUseCase,
    private val plusDishUseCase: PlusDishUseCase
) : ViewModel() {

    private val _basketItems = MutableLiveData<List<DishItem>>()
    val basketItems: LiveData<List<DishItem>> = _basketItems

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    init {
        loadBasketItems()
    }
    private fun calculateTotalPrice(dishList: List<DishItem>) {
        var total = 0.0
        for (dish in dishList) {
            total += dish.price * dish.count
            Log.d("BasketViewModel",
                "calculateTotalPrice: dish.name = ${dish.name}," +
                        " dish.price = ${dish.price}," +
                        " dish.count = ${dish.count}, total = $total")
        }
        _totalPrice.value = total
    }
    // загрузка элементов корзины
    fun loadBasketItems() {
        viewModelScope.launch {
            getAllBasketUseCase().collectLatest { items ->
                _basketItems.value = items
                calculateTotalPrice(items)
                Log.d("loadingBasket","ok = ${_basketItems}")
            }
        }
    }
    //увелечение кол-ва на 1
    fun onPlusClicked(dishItem : DishItem){
        viewModelScope.launch {
            try {
                plusDishUseCase(dishItem.id)
                loadBasketItems()
            } catch (e: Exception) {
                Log.e("BasketViewModel", "Ошибка при увеличении количества", e)
            }
        }
    }
    //уменьшение кол-ва на 1
    fun onMinusClicked(dishItem: DishItem) {
        viewModelScope.launch {
            try {
                minusDishUseCase(dishItem.id)
                loadBasketItems()
            } catch (e: Exception) {
                Log.e("BasketViewModel", "Ошибка при уменьшении количества", e)
            }
        }
    }
    //удаление товара если < 1
    fun onDeleteClicked(dishItem : DishItem){
        viewModelScope.launch {
            try {
                deleteDishBasketUseCase(dishItem.id)
                loadBasketItems()
            } catch (e: Exception) {
                Log.e("BasketViewModel", "Ошибка при удалении количества", e)
            }
        }
    }
    fun addDishToBasket(dishId: Int) {
        viewModelScope.launch {
            try {
                addDishToBasketUseCase.execute(dishId)
                loadBasketItems()
            } catch (e: Exception) {
                Log.e("BasketViewModel", "Ошибка добавления блюда: ${e.message}", e)
            }
        }
    }
}