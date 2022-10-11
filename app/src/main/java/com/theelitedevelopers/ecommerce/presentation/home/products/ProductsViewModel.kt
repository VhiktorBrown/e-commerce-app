package com.theelitedevelopers.ecommerce.presentation.home.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theelitedevelopers.ecommerce.domain.model.Product
import com.theelitedevelopers.ecommerce.domain.usecases.ProductUseCases
import com.theelitedevelopers.ecommerce.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.FieldPosition
import javax.inject.Inject

/**
 * @created 08/10/2022 - 6:53 PM
 * @project Ecommerce app
 * @author The Elite Developers
 */

/** @HiltViewModel will make models to be
 * created using Hilt's model factory
 * @Inject annotation will be used
 * to inject all dependencies to view model class
 *
*/
@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel() {
    var productsScreenState : ProductsScreenState = ProductsScreenState()
    private var productsScreenStateLiveData : MutableLiveData<ProductsScreenState> = MutableLiveData<ProductsScreenState>()

    init {
        fetchProducts()
        fetchBrandNames(productsScreenState.products)
    }

    /**
     * This is our observer. With this function, we'll
     * be observing for when data changes. So an activity
     * or a fragment can implement this function to monitor
     * data changes and make necessary changes in our UI.
     */
     fun fetchProductData() : MutableLiveData<ProductsScreenState> {
        return productsScreenStateLiveData;
    }

    private fun setProductData(productsScreenState: ProductsScreenState){
        productsScreenStateLiveData.value = productsScreenState
    }

     fun fetchProducts(){
        //This function returns a Flow response Object
        productUseCases.products().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    productsScreenState = productsScreenState.copy(isLoading = true, message = null)
                    setProductData(productsScreenState)
                }
                is Resource.Error -> {
                    productsScreenState = productsScreenState.copy(isLoading = false, message = result.message)
                    setProductData(productsScreenState)

                }
                is Resource.Success -> {
                    productsScreenState = productsScreenState.copy(isLoading = false, message = null)
                    productsScreenState = productsScreenState.copy(products = result.data?: emptyList())

                    setProductData(productsScreenState)

                    /**
                     * Next, with the List that we have,
                     * we need to sieve out all the brand
                     * names without avoiding duplicate brand
                     * names showing up
                     */
                    fetchBrandNames(productsScreenStateLiveData.value!!.products)
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * This function is responsible for fetching
     * just the brand names from our List
     */
    private fun fetchBrandNames(products : List<Product>){
        productUseCases.fetchBrandNames(products).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        productsScreenState = productsScreenState.copy(isLoading = true, message = null)
                    }
                    is Resource.Error -> {
                        productsScreenState = productsScreenState.copy(isLoading = false, message = "Could not fetch brand names")
                        setProductData(productsScreenState)
                    }
                    is Resource.Success -> {
                        productsScreenState = productsScreenState.copy(isLoading = false, message = null, brandNames = result.data!!)
                        setProductData(productsScreenState)
                    }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * This function fetches all the products a brand has.
     * So, it calls another class that has the function that
     * will sift through the List and fetch only products of
     * the brand name passed as an argument.
     */

     fun fetchBrandProducts(brandName: String, products: List<Product>){
        productUseCases.fetchBrandProducts(brandName, products).onEach { result ->
            when(result) {
                is Resource.Success -> {
                   productsScreenState = productsScreenState.copy(brandProducts = result.data!!, message = null)
                }
                else -> {
                   productsScreenState = productsScreenState.copy(message = "Could not fetch brand products")
                }
            }
            setProductData(productsScreenState)
        }.launchIn(viewModelScope)
    }

//    /**
//     * THis function fetches just the brand name from the
//     * position passed as an argument
//     */
//    private fun fetchBrandName(brandName: String){
//        for(product in productsScreenState.products){
//            if(brandName == product.brand){
//
//            }
//        }
}