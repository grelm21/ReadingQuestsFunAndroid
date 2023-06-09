package com.example.readingquestsfun.koin

import com.example.readingquestsfun.repository.EquipmentRepository
import com.example.readingquestsfun.viewModels.EquipmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoModule = module {
    single { EquipmentRepository() }
}

val viewModelModule = module {
    viewModel { EquipmentViewModel(get()) }
}