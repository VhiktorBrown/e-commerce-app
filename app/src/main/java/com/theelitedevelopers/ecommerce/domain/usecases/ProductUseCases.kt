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

/**
 * @created 09/10/2022 - 06:03 PM
 * @project Ecommerce app
 * @author The Elite Developers
 */

class ProductUseCases @Inject constructor(
    private val productRepository: ProductRepository,
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
    fun fetchBrandProducts(brand : String, products : List<Product>) : Flow<Resource<List<Product>>> = flow  {
        var list : MutableList<Product> = mutableListOf();

        /**
         * The 'All' brand name should display
         * all products. If it is any other
         * brand name, then we loop through
         * the product list to fetch all products
         * associated with that brand name.
         */
        try{
            emit(Resource.Loading())
            if(brand == "All"){
                list = products.toMutableList()
            }else {
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
            }
            emit(Resource.Success(list))
        }catch (e : IOException){
            emit(Resource.Error(message = "Could not fetch products", data = null))
        }
    }


    fun fetchBrandNames(products: List<Product>) : Flow<Resource<List<String>>> = flow {
        val brandNames : MutableList<String> = mutableListOf()

        //This variable tracks if a brand name already exists in the new list
        var exists = false;
        /**
         * Here, we need to fetch the Brands from the bulky
         * Product List. We loop through the list fetching just
         * brand names.
         */

        /**
         * This brand Category(ALL) is going to be
         * responsible for displaying all the
         * products by all teh brands
         */
        brandNames.add("All")

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

            emit(Resource.Success(brandNames))
        }
    }
}