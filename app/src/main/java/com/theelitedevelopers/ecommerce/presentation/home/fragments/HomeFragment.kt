package com.theelitedevelopers.ecommerce.presentation.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.theelitedevelopers.ecommerce.databinding.FragmentHomeBinding
import com.theelitedevelopers.ecommerce.presentation.home.fragments.adapter.BrandAdapter
import com.theelitedevelopers.ecommerce.presentation.home.fragments.adapter.ProductAdapter
import com.theelitedevelopers.ecommerce.presentation.home.fragments.interfaces.OnItemClicked
import com.theelitedevelopers.ecommerce.presentation.home.products.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @created 08/10/2022 - 6:53 PM
 * @project Ecommerce app
 * @author The Elite Developers
 */

@AndroidEntryPoint
class HomeFragment : Fragment(), OnItemClicked {
    private var _binding : FragmentHomeBinding? = null
    private var brandAdapter : BrandAdapter? = null
    private var productAdapter : ProductAdapter? = null
    private val binding get() = _binding
    private val productsViewModel : ProductsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initViews()

        return binding!!.root
    }

    private fun initViews(){
        //Initialize Layout Manager for Products List
        val productLayoutManager : RecyclerView.LayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding?.productsRecyclerView?.layoutManager = productLayoutManager

        //Initialize LayoutManager for Brands List
        val brandLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding?.brandsRecyclerView?.layoutManager = brandLayoutManager

        productsViewModel.fetchProductData().observe(viewLifecycleOwner, {
            //once any change has been made to the Product list...

            if(it.message != null){
                binding?.retryButton?.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }

            if(it.isLoading){
                showProgressBar()
            }else {
                hideProgressBar()

                if(it.products.isNotEmpty()){
                    productAdapter = ProductAdapter(requireActivity(), it.products)
                    binding?.productsRecyclerView?.adapter = productAdapter
                }

                if(it.brandNames.isNotEmpty()){
                    brandAdapter = BrandAdapter(requireActivity(), it.brandNames, this)
                    binding?.brandsRecyclerView?.adapter = brandAdapter
                }
            }
        })

        //Make server request Again when user clicks on this button
        binding?.retryButton?.setOnClickListener {
            //hide TRY AGAIN button and display Progress Bar
            showProgressBar()
            productsViewModel.fetchProducts()
        }

    }

    private fun hideProgressBar(){
        binding?.progressBar?.visibility = View.GONE
        binding?.productsRecyclerView?.visibility = View.VISIBLE
    }

    private fun showProgressBar(){
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.retryButton?.visibility = View.GONE
        binding?.productsRecyclerView?.visibility = View.GONE
    }

    override fun OnClicked(brandName: String) {
        /**
         * Here, we'll receive the current brandName from the
         * Recycler View - that displays list of brands.
         *
         * So, each time our user clicks on a brand name,
         *  we fetch only products that are
         * associated with that brand.
         */
//        productsViewModel.fetchBrandProducts(brandName, productsViewModel.fetchProductData().value!!.products)
//        productAdapter = ProductAdapter(requireActivity(), productsViewModel.fetchProductData().value!!.brandProducts)
//        binding?.productsRecyclerView?.adapter = productAdapter
    }
}