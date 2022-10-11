package com.theelitedevelopers.ecommerce.data.repository

import com.theelitedevelopers.ecommerce.data.ApiInterface
import com.theelitedevelopers.ecommerce.domain.model.Product
import com.theelitedevelopers.ecommerce.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * @created 09/10/2022 - 10:03 PM
 * @project Ecommerce app
 * @author The Elite Developers
 */

/**
 * This has been configured with Hilt
 * which means that Hilt helps us create
 * an instance of this class behind the scenes
 * so that we don't have to initialize it ourselves.
 *
 * Same goes for the API INTERFACE class that we inject.
 */
class ProductRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : ProductRepository{

    override suspend fun fetchAllProducts(): List<Product> {
        return apiInterface.fetchAllProducts()
    }
}