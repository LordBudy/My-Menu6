package com.example.mymenu.Presentation.ViewModels.Factoryes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymenu.Domain.Basket.AddDishToBasketUseCase
import com.example.mymenu.Domain.Menu.Search.GetSearchDishesUseCase
import com.example.mymenu.Domain.MenuMini.GetDishMiniUseCase
import com.example.mymenu.Presentation.ViewModels.MenuMiniViewModel
import com.example.mymenu.Presentation.ViewModels.SearchViewModel

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
    private val getSearchDishesUseCase: GetSearchDishesUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                return SearchViewModel(getSearchDishesUseCase) as T
            }
            throw IllegalArgumentException("Неизвестный класс ViewModel")
        }

}