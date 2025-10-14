package org.aleksandrilinskii.di

import org.aleksandrilinskii.manage_product.util.PhotoPicker
import org.koin.core.module.Module
import org.koin.dsl.module

actual val targetModule: Module = module {
    single<PhotoPicker> { PhotoPicker() }
}