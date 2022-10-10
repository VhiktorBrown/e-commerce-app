package com.theelitedevelopers.ecommerce.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductColor(
    @SerializedName("hex_value")
    val hexValue : String,
    @SerializedName("colour_name")
    val colorName : String,
) : Parcelable