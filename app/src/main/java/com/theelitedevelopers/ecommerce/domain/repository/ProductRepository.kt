package com.theelitedevelopers.ecommerce.domain.repository

import com.theelitedevelopers.ecommerce.domain.model.Product

interface ProductRepository {

    suspend fun fetchAllProducts() : List<Product>
}