package com.example.mymenu.core.category.di

import com.example.mymenu.core.category.data.CategoryRepositoryImpl
import com.example.mymenu.core.category.domain.CategoryRepository
import com.example.mymenu.core.category.domain.GetCategoryUseCase
import com.example.mymenu.core.category.presentation.viewModel.CategoryViewModel
import com.example.mymenu.core.data.ApiService.CatDataSource
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categorydi = module {
    single {
        CatDataSource()
    }
    single<CategoryRepository> {
        CategoryRepositoryImpl(catDataSource = get())
    }
    factory {
        GetCategoryUseCase(categoryRepository = get())
    }
    viewModel{
        CategoryViewModel(
            getCategoryUseCase = get()
        )
    }
}