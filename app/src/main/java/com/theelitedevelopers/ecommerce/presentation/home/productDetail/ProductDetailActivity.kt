package com.theelitedevelopers.ecommerce.presentation.home.productDetail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import com.squareup.picasso.Picasso
import com.theelitedevelopers.ecommerce.R
import com.theelitedevelopers.ecommerce.databinding.ActivityProductDetailBinding
import com.theelitedevelopers.ecommerce.databinding.FragmentHomeBinding
import com.theelitedevelopers.ecommerce.domain.model.Product
import com.theelitedevelopers.ecommerce.utils.Constants

/**
 * @created 09/10/2022 - 7:58 PM
 * @project Ecommerce app
 * @author The Elite Developers
 */

private var binding : ActivityProductDetailBinding? = null
var product : Product? = null

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        /**
         * I know. This is a rather weird way for
         * recovering extras from Intents. The reason
         * for this is that I'm using Android's latest
         * API(Compile Version 33).So in this API,
         * getParcelableExtra is DEPRECATED.
         *
         * That explains the need for the conditional
         * statement.
         */
        //fetch Product Details from Intent
        product = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(Constants.PRODUCT_DETAIL, Product::class.java)!!
        } else {
            intent.getParcelableExtra<Product>(Constants.PRODUCT_DETAIL)!!
        }

        initViews()
    }

    private fun initViews(){
        /**
         * Nothing much here. Just
         * Set up and display details
         * of products that have been passed
         * through Intents
         */

        binding!!.productName.text = product?.name
        binding!!.brand.text = product?.brand
        binding!!.category.text = product?.productType
        binding!!.description.text = product?.description
        binding!!.productPrice.text = product?.addPriceSign()

        //Load the Product Image
        Picasso.get()
            .load("http:" +product?.featuredImage)
            .placeholder(R.drawable.ecommerce)
            .into(binding!!.productImage)

        binding?.backArrow?.setOnClickListener {
            onBackPressed()
        }
    }
}