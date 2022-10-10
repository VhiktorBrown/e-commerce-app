package com.theelitedevelopers.ecommerce.presentation.home.products

import androidx.lifecycle.MutableLiveData
import com.theelitedevelopers.ecommerce.domain.model.Product
import com.theelitedevelopers.ecommerce.domain.usecases.ProductUseCases
import com.theelitedevelopers.ecommerce.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/** @HiltViewModel will make models to be
 * created using Hilt's model factory
 * @Inject annotation will be used
 * to inject all dependencies to view model class
 *
*/
@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
){
    var productsScreenState : ProductsScreenState = ProductsScreenState()
    private lateinit var productsScreenStateLiveData : MutableLiveData<ProductsScreenState>
    init {
        fetchProducts()
    }

    private fun fetchProducts(){
        //This function returns a Flow response Object
        productUseCases.products().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    productsScreenState = productsScreenState.copy(isLoading = true)
                    productsScreenStateLiveData.value = productsScreenState
                }
                is Resource.Error -> {
                    productsScreenState = productsScreenState.copy(isLoading = false, message = result.message)
                    productsScreenStateLiveData.value = productsScreenState
                }
                is Resource.Success -> {
                    productsScreenState = productsScreenState.copy(isLoading = false)
                    productsScreenState = productsScreenState.copy(products = result.data?: emptyList())

                    productsScreenStateLiveData.value = productsScreenState

                    /**
                     * Next, with the List that we have,
                     * we need to sieve out all the brand
                     * names without avoiding duplicate brand
                     * names showing up
                     */
                    fetchBrandNames(productsScreenStateLiveData.value!!.products)
                }
            }
        }
    }

    /**
     * This function is responsible for fetching
     * just the brand names from our List
     */
    private fun fetchBrandNames(products : List<Product>){
        productUseCases.fetchBrandNames(products).onEach { result ->
            run {
                when (result) {
                    is Resource.Loading -> {
                        productsScreenState = productsScreenState.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        productsScreenState = productsScreenState.copy(isLoading = false)
                        productsScreenStateLiveData.value = productsScreenState
                    }

                    is Resource.Success -> {
                        productsScreenState = productsScreenState.copy(isLoading = false, brandNames = result.data!!)
                        productsScreenStateLiveData.value = productsScreenState
                    }
                }
            }
        }

        //TODO Launch with ViewModelScope here
    }

    /**
     * This function fetches all the products a brand has.
     * So, it calls another class that has the function that
     * will sift through the List and fetch only products of
     * the brand name passed as an argument.
     */
    private fun fetchBrandProducts(brandName: String, products: List<Product>){
        productUseCases.fetchBrandProducts(brandName, products).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    productsScreenState = productsScreenState.copy(products = result.data!!)
                }
            }
        }
    }

}