package com.theelitedevelopers.ecommerce.presentation.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.theelitedevelopers.ecommerce.R
import com.theelitedevelopers.ecommerce.databinding.ActivityMainBinding
import com.theelitedevelopers.ecommerce.presentation.home.products.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("StaticFieldLeak")
lateinit var navController : NavController;
private var binding : ActivityMainBinding? = null;


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        /**
         * Here, we set up configuration for Navigation Component
         * We tell our Nav Controller how to find our host fragment
         * and then it intuitively handles navigation through the
         * different fragments because we've defined those in our
         * navigation XML file
         */
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_dashboard) as NavHostFragment
        navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.navigation).setupWithNavController(navController)
    }
}