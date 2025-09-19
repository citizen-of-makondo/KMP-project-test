package org.aleksandrilinskii.di

import com.aleksandrilinskii.auth.AuthViewModel
import org.aleksandrilinskii.data.CustomerRepositoryImpl
import org.aleksandrilinskii.data.domain.CustomerRepository
import org.aleksandrilinskii.home.HomeGraphViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
}

fun initializeKoinModule(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule)
    }
}