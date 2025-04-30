package com.example.mymenu.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymenu.Domain.Menu.GetDishsMenuUseCase
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.Presentation.ViewModels.Interfaces.MenuInterface
import kotlinx.coroutines.launch

// MenuViewModel - ViewModel для фрагмента Menu
class MenuViewModel(
    private val getDishsMenuUseCase: GetDishsMenuUseCase,
    private val categogyId: Int
) : ViewModel() {

    var menuInterface: MenuInterface? = null

    private val _dishs = MutableLiveData<List<DishItem>>()

    val dishs: LiveData<List<DishItem>> = _dishs

    // Инициализатор - вызывается при создании экземпляра MenuViewModel
    init {
        // Загружаем блюда при создании ViewModel, передавая ID категории
        loadDishs()
    }

    private fun loadDishs() {
        //  viewModelScope - корутин скоуп, связанный с жизненным циклом ViewModel
        viewModelScope.launch {
            try {
                //  Вызываем UseCase для получения списка блюд по ID категории
                val dishList = getDishsMenuUseCase.execute(categogyId)   // Передаем categoryId в use case
                //  Присваиваем полученный список блюд переменной _dishs
                _dishs.value = dishList
                //  Вызываем метод интерфейса для отображения списка блюд
                menuInterface?.showMenu(dishList)
            } catch (e: Exception) {
                println("Error loading dishs: ${e.message}")
                _dishs.value = emptyList()
            }
        }
    }

}