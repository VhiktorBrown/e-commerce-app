package com.theelitedevelopers.ecommerce.presentation.home.products

import com.theelitedevelopers.ecommerce.domain.model.Product

data class ProductsScreenState(
    var isLoading : Boolean = false,
    var selectedBrand : String = "all",
    var products : List<Product> = emptyList()
) {
}