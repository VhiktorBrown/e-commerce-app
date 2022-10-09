package com.theelitedevelopers.ecommerce.presentation.home.products

import com.theelitedevelopers.ecommerce.domain.usecases.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
){
    val productsScreenState : ProductsScreenState = ProductsScreenState()

    init {

    }
}