package com.theelitedevelopers.ecommerce.domain.repository

import com.theelitedevelopers.ecommerce.domain.model.Product

/**
 * @created 08/10/2022 - 10:03 PM
 * @project Ecommerce app
 * @author The Elite Developers
 */

interface ProductRepository {

    suspend fun fetchAllProducts() : List<Product>
}