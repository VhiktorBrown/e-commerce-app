package com.theelitedevelopers.ecommerce.presentation.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.theelitedevelopers.ecommerce.R
import com.theelitedevelopers.ecommerce.databinding.FragmentHomeBinding
import com.theelitedevelopers.ecommerce.presentation.home.fragments.adapter.BrandAdapter
import com.theelitedevelopers.ecommerce.presentation.home.fragments.interfaces.OnItemClicked

class HomeFragment : Fragment(), OnItemClicked {
    private var _binding : FragmentHomeBinding? = null
    private var brandAdapter : BrandAdapter? = null
    private val binding get() = _binding

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
        //Initialize Layout Manager
        val layoutManager : RecyclerView.LayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding?.brandsRecyclerView?.layoutManager = layoutManager

        //Initialize the brand Adapter responsible for displaying brands
        brandAdapter = BrandAdapter(requireActivity(), emptyList(), this)
    }

    override fun OnClicked(position: Int) {
        /**
         * Here, we'll receive the current position of the
         * Recycler View - that displays list of brands
         */

    }
}