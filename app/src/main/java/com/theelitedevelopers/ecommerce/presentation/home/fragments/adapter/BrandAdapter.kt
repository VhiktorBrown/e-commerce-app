package com.theelitedevelopers.ecommerce.presentation.home.fragments.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.theelitedevelopers.ecommerce.R
import com.theelitedevelopers.ecommerce.databinding.BrandItemBinding
import com.theelitedevelopers.ecommerce.presentation.home.fragments.interfaces.OnItemClicked

/**
 * @created 08/10/2022 - 6:53 PM
 * @project Ecommerce app
 * @author The Elite Developers
 */

class BrandAdapter(var context : Context, private var brandList : List<String>,
                   private var listener: OnItemClicked
) : RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {

    private var selectedPosition : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val binding : BrandItemBinding = BrandItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BrandViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: BrandViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.binding.brandName.text = brandList[position]
        /***********
         * for setting the background for item currently selected
         * or clicked by the user on
         * the recyclerview
         */

        if (selectedPosition == position) {
            holder.binding.brandLayout.setBackground(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.background
                )
            )
            //change the color of text
            holder.binding.brandName.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.binding.brandLayout.setBackground(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.background_white
                )
            )
            holder.binding.brandName.setTextColor(ContextCompat.getColor(context, R.color.black))
        }


        holder.binding.root.setOnClickListener {

            val position : Int = holder.adapterPosition
            /**
             * This sets the currently selected position
             * and reflects that to the HomeFragment and
             * passes the name of the brand to our Fragment
             * with which we fetch the products with.
             */

            listener.OnClicked(brandList[position], position)
            notifyItemChanged(selectedPosition)
            selectedPosition = position
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount(): Int {
        return brandList.size
    }

    class BrandViewHolder(var binding : BrandItemBinding) : RecyclerView.ViewHolder(binding.root)
}