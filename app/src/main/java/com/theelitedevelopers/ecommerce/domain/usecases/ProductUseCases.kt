package com.theelitedevelopers.ecommerce.domain.usecases

import com.theelitedevelopers.ecommerce.domain.model.Product
import com.theelitedevelopers.ecommerce.domain.repository.ProductRepository
import com.theelitedevelopers.ecommerce.presentation.home.products.ProductsScreenState
import com.theelitedevelopers.ecommerce.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductUseCases @Inject constructor(
    private val productRepository: ProductRepository,
    //private val productsScreenState: ProductsScreenState
) {

    //Function that fetches all products from the server
    fun products() : Flow<Resource<List<Product>>> = flow {
        try{
            emit(Resource.Loading())
            val response = productRepository.fetchAllProducts()
            emit(Resource.Success(response))
        }catch (e : HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
        }catch (e : IOException){
            emit(Resource.Error(e.localizedMessage ?: "Could not reach server. Check your internet connection."))
        }
    }

    //Function that sifts through and fetches only products related to a specific brand
    fun fetchBrandProducts(brand : String, products : List<Product>) : Flow<Resource<List<Product>>> = flow {
        val list : MutableList<Product> = mutableListOf();

        /**
         * Looping through the Product List, we
         * look for products that have the brand
         * name that has been passed to this function
         * and then add these brand products to a new
         * list, then return it.
         */

        for(product in products){
            if(product.brand == brand){
                list.add(product)
            }
        }
        emit(Resource.Success(list))
    }


    fun fetchBrandNames(products: List<Product>) : Flow<Resource<List<String>>> = flow {
        val brandNames : MutableList<String> = mutableListOf()

        //This variable tracks if a brand name already exists in the new list
        var exists : Boolean = false;
        /**
         * Here, we need to fetch the Brands from the bulky
         * Product List. We loop through the list fetching just
         * brand names.
         */

        for(product in products){
            for(item in brandNames){
                if(product.brand == item){
                    exists = true
                }
            }

            /**
             * After looping through the BrandNames list
             * to check if the current Item(Brand Name) in
             * the iteration has been added, we set the EXISTS
             * variable back to False for the next time that
             * we run a new iteration with a new Product brand name.
             *
             * Also, if the EXISTS variable is still false, then we
             * add the Product brand name to the BRAND_NAMES List
             */
            if(!exists){
                brandNames.add(product.brand)
            }

            exists = false

            //The Parent iteration continues....
        }
    }
}