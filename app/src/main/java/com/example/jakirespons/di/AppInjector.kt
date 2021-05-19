package com.example.jakirespons.di

import com.example.jakirespons.mvvm.category.CategoryViewModel
import com.example.jakirespons.mvvm.category.description.DescriptionViewModel
import com.example.jakirespons.mvvm.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
    viewModel { CategoryViewModel() }
    viewModel { DescriptionViewModel() }
}