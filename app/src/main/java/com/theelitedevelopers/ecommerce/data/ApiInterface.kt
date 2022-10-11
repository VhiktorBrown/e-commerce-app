package com.theelitedevelopers.ecommerce.data

import com.theelitedevelopers.ecommerce.domain.model.Product
import retrofit2.http.GET

/**
 * @created 09/10/2022 - 10:03 PM
 * @project Ecommerce app
 * @author The Elite Developers
 */

interface ApiInterface {

    //fetch All Products from server
    @GET("products.json")
    suspend fun fetchAllProducts() : List<Product>
}