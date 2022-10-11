package com.theelitedevelopers.ecommerce.presentation.home.fragments.adapter

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

class BrandAdapter(var context : Context, var brandList : List<String>,
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

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.binding.brandName.text = brandList[position]

        /***********
         * for setting the background for item currently in display on
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


//        holder.binding.root.setOnClickListener {
//            var position = holder.adapterPosition
//
//            /**
//             * This sets the currently selected position
//             * and reflects that to the HomeFragment
//             */
//            if(position != RecyclerView.NO_POSITION){
//                listener.OnClicked(brandList[position])
//                notifyItemChanged(selectedPosition)
//                selectedPosition = position
//                notifyItemChanged(selectedPosition)
//            }
//        }
    }

    fun setSelectedIndex(position: Int){
        selectedPosition = position;
    }

    override fun getItemCount(): Int {
        return brandList.size
    }

    class BrandViewHolder(var binding : BrandItemBinding) : RecyclerView.ViewHolder(binding.root)
}