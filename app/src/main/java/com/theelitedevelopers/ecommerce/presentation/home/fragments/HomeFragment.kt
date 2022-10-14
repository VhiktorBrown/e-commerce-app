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
    private lateinit var productLayoutManager : RecyclerView.LayoutManager
    private lateinit var brandLayoutManager : LinearLayoutManager


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
        productLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding?.productsRecyclerView?.layoutManager = productLayoutManager
        binding?.productsRecyclerView?.hasFixedSize()

        //Initialize LayoutManager for Brands List
        brandLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding?.brandsRecyclerView?.layoutManager = brandLayoutManager
        binding?.brandsRecyclerView?.hasFixedSize()

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
            }

            /**
             * Firstly, we check if the Adapter has been
             * initialized before. If it has not, we initialise it
             * If it has, we just set add the new list to our
             * adapter and call notifyDataSetChanged().
             */
            if(productAdapter != null){
                productAdapter!!.setList(it.brandProducts)
            }else {
                if(it.brandProducts.isNotEmpty()){
                    productAdapter = ProductAdapter(requireActivity(), it.brandProducts)
                    binding?.productsRecyclerView?.adapter = productAdapter
                }
            }


            //As long as it has not been fetched, keep checking if it's empty or not.
            //This variable is responsible for telling us if the brandNames have already been fetched.
            //If it has not, we initialize our Brand Adapter only once
            if(!it.fetchedBrandNames){
                if(it.brandNames.isNotEmpty()){
                    brandAdapter = BrandAdapter(requireActivity(), it.brandNames, this)
                    binding?.brandsRecyclerView?.adapter = brandAdapter

                    //Once added, set it to true - telling our viewModel to register that our BrandNames have been fetched
                    productsViewModel.setFetchedBrandNamesValue()
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
    }

    private fun showProgressBar(){
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.retryButton?.visibility = View.GONE
    }

    override fun OnClicked(brandName: String, position : Int) {
        /**
         * Here, we'll receive the current brandName from the
         * Recycler View - that displays list of brands.
         *
         * So, each time our user clicks on a brand name,
         *  we fetch only products that are
         * associated with that brand.
         */

        //calls the function responsible for fetching products that belong this Brand
        productsViewModel.fetchBrandProducts(brandName)
    }

    /**
     * So, if User leaves this fragment and
     * later returns back to it, naturally our
     * data is no longer visible on the fragment.
     *
     * So, we use this function to restore our data
     * to it's previous state. Thanks to our ViewModel
     * that has been handling State Management since 1896.
     */

    private fun displayInForForUser(){
        if(productsViewModel.productsScreenState.fetchedBrandNames){
            productAdapter = ProductAdapter(requireActivity(), productsViewModel.productsScreenState.brandProducts)
            binding?.productsRecyclerView?.adapter = productAdapter

            brandAdapter = BrandAdapter(requireActivity(), productsViewModel.productsScreenState.brandNames, this)
            binding?.brandsRecyclerView?.adapter = brandAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        displayInForForUser()
    }
}