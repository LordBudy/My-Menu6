package com.example.mymenu.category.di

import com.example.mymenu.category.presentation.viewModel.CategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val catPresentDi = module {
    viewModel{
        CategoryViewModel(
            getCategoryUseCase = get()
        )
    }
}