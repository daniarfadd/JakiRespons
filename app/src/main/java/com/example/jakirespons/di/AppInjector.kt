package com.example.jakirespons.di

import com.example.jakirespons.mvvm.lapor.LaporViewModel
import com.example.jakirespons.mvvm.lapor.category.CategoryViewModel
import com.example.jakirespons.mvvm.lapor.category.description.DescriptionViewModel
import com.example.jakirespons.mvvm.main.MainViewModel
import com.example.jakirespons.utils.Location
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
    viewModel { LaporViewModel() }
    viewModel { CategoryViewModel() }
    viewModel { DescriptionViewModel() }

    single { Location(androidContext()) }
}