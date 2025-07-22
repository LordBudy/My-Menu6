package com.example.mymenu.basket.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.basket.data.BasketRepositoryImpl
import com.example.mymenu.basket.domain.GetAllBasketUseCase
import com.example.mymenu.coreModels.DishItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BasketViewModel(
    private val getAllBasketUseCase: GetAllBasketUseCase,
    private val basketRepository: BasketRepositoryImpl

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
                basketRepository.plusDish(dishItem.id)
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
                basketRepository.minusDish(dishItem.id)
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
                basketRepository.deleteDishBasket(dishItem.id)
                loadBasketItems()
            } catch (e: Exception) {
                Log.e("BasketViewModel", "Ошибка при удалении количества", e)
            }
        }
    }
    fun addDishToBasket(dishId: Int) {
        viewModelScope.launch {
            try {
                basketRepository.addDishToBasket(dishId) // Use the repository to add the dish.
                loadBasketItems()
            } catch (e: Exception) {
                Log.e("BasketViewModel", "Error adding dish: ${e.message}", e)
            }
        }
    }
}