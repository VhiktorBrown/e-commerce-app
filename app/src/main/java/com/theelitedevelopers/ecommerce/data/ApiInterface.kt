package com.theelitedevelopers.ecommerce.data

import com.theelitedevelopers.ecommerce.domain.model.Product
import retrofit2.http.GET

interface ApiInterface {

    //fetch All Products
    @GET("products.json")
    suspend fun fetchAllProducts() : List<Product>
}