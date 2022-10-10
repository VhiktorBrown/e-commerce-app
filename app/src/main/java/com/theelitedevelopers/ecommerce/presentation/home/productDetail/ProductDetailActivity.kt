package com.theelitedevelopers.ecommerce.presentation.home.productDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import com.squareup.picasso.Picasso
import com.theelitedevelopers.ecommerce.R
import com.theelitedevelopers.ecommerce.databinding.ActivityProductDetailBinding
import com.theelitedevelopers.ecommerce.databinding.FragmentHomeBinding
import com.theelitedevelopers.ecommerce.domain.model.Product
import com.theelitedevelopers.ecommerce.utils.Constants

private var binding : ActivityProductDetailBinding? = null
var product : Product = TODO()

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //fetch Product Details from Intent
        product = intent.getParcelableExtra(Constants.PRODUCT_DETAIL)!!

        initViews()
    }

    private fun initViews(){
        /**
         * Set up and display details
         * of products that have been passed
         */

        binding!!.productName.text = product.name
        binding!!.brand.text = product.brand
        binding!!.category.text = product.productType
        binding!!.description.text = product.description
        binding!!.productPrice.text = product.addPriceSign()

        //Load the Product Image
        Picasso.get()
            .load(product.featuredImage)
            .placeholder(R.drawable.ecommerce)
            .into(binding!!.productImage)


    }
}