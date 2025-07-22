package com.example.mymenu.category.di

import com.example.mymenu.category.data.CategoryRepositoryImpl
import com.example.mymenu.category.domain.CategoryRepository
import com.example.mymenu.coreData.ApiService.CatDataSource
import org.koin.dsl.module

val catDataDi = module {
    single {
        CatDataSource()
    }
    single<CategoryRepository> {
        CategoryRepositoryImpl(catDataSource = get())
    }

}