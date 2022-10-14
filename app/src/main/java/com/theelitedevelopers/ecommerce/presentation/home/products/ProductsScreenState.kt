package com.theelitedevelopers.ecommerce.presentation.home.products

import com.theelitedevelopers.ecommerce.domain.model.Product

/**
 * @created 08/10/2022 - 6:53 PM
 * @project Ecommerce app
 * @author The Elite Developers
 */

/**
 * This is custom helper class that
 * will help us with keeping our data
 * organized in one place.
 */
data class ProductsScreenState(
    var isLoading : Boolean = false,
    var fetchedBrandNames : Boolean = false,
    var selectedBrand : String = "all",
    var message : String? = "",
    var products : List<Product> = emptyList(),
    var brandProducts : List<Product> = emptyList(),
    var brandNames : List<String> = emptyList()
) {
}