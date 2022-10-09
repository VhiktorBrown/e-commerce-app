package com.theelitedevelopers.ecommerce.domain.model

data class Product(
    val id : Int,
    val name : String,
    val brand : String,
    val price : String,
    val priceSign : String,
    val currency : String,
    val imageLink : String,
    val productLink : String,
    val websiteLink : String,
    val description : String,
    val category : String,
    val productType : String,
    val tagList : List<String>,
    val featuredImage : String,
    val productColors : List<ProductColor>
) {
    fun addPriceSign() : String{
        return "$priceSign $price";
    }
}