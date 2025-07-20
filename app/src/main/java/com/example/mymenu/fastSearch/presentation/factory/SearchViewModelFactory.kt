package com.example.mymenu.fastSearch.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymenu.fastSearch.domain.GetSearchDishesUseCase
import com.example.mymenu.fastSearch.presentation.viewModel.SearchViewModel

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