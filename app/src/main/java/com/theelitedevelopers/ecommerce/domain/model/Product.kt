package com.theelitedevelopers.ecommerce.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id : Int,
    val name : String,
    val brand : String,
    val price : String,
    @SerializedName("price_sign")
    val priceSign : String,
    val currency : String,
    @SerializedName("image_link")
    val imageLink : String,
    @SerializedName("product_link")
    val productLink : String,
    @SerializedName("website_link")
    val websiteLink : String,
    val description : String,
    val category : String,
    @SerializedName("product_type")
    val productType : String,
    @SerializedName("tag_list")
    val tagList : List<String>,
    @SerializedName("api_featured_image")
    val featuredImage : String,
    @SerializedName("product_colors")
    val productColors : List<ProductColor>
) : Parcelable {
    fun addPriceSign() : String{
        return "$priceSign $price";
    }
}