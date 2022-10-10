package com.theelitedevelopers.ecommerce.presentation.home.fragments.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.theelitedevelopers.ecommerce.R
import com.theelitedevelopers.ecommerce.databinding.BrandItemBinding
import com.theelitedevelopers.ecommerce.databinding.ProductItemBinding
import com.theelitedevelopers.ecommerce.domain.model.Product
import com.theelitedevelopers.ecommerce.presentation.home.fragments.interfaces.OnItemClicked
import com.theelitedevelopers.ecommerce.presentation.home.productDetail.ProductDetailActivity
import com.theelitedevelopers.ecommerce.utils.Constants

class ProductAdapter(var context : Context, var productList : List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding : ProductItemBinding = ProductItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        //set and display details of each product
        holder.binding.productName.text = productList[position].name
        holder.binding.productPrice.text = productList[position].addPriceSign()
        holder.binding.productType.text = productList[position].productType

        //Load the Product Image
        Picasso.get()
            .load(productList[position].featuredImage)
            .placeholder(R.drawable.ecommerce)
            .into(holder.binding.productImage)

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
                .putExtra(Constants.PRODUCT_DETAIL, productList[position])
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(var binding : ProductItemBinding) : RecyclerView.ViewHolder(binding.root)
}