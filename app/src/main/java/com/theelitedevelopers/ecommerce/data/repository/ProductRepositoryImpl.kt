package com.theelitedevelopers.ecommerce.data.repository

import com.theelitedevelopers.ecommerce.data.ApiInterface
import com.theelitedevelopers.ecommerce.domain.model.Product
import com.theelitedevelopers.ecommerce.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : ProductRepository{

    override suspend fun fetchAllProducts(): List<Product> {
        return apiInterface.fetchAllProducts()
    }
}