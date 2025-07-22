package com.example.mymenu.category.di

import com.example.mymenu.category.domain.GetCategoryUseCase
import org.koin.dsl.module

val catDomainDi = module {

    factory {
        GetCategoryUseCase(categoryRepository = get())
    }

}