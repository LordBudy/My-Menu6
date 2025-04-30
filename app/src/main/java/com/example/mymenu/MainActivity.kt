package com.example.mymenu

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mymenu.Domain.Models.DishItem
import com.example.mymenu.Presentation.Fragments.MenuMini
import com.example.mymenu.Presentation.ViewModels.Interfaces.MenuMiniListener
import com.google.android.material.bottomnavigation.BottomNavigationView

// @Suppress("DEPRECATION") - подавляем предупреждения о использовании устаревших API
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), MenuMiniListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bNav)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.Container_frag) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home,
                R.id.account,
                R.id.menuMini,
                R.id.basket
            )
        )

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Setup ActionBar after setting up navController
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            backButton.visibility = if (appBarConfiguration.topLevelDestinations.contains(destination.id)) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    if (navController.currentDestination?.id != R.id.home) {
                        clearBackStack(R.id.home)
                        navController.navigate(R.id.home)
                    }
                    true
                }
                R.id.fastSearch -> {
                    if (navController.currentDestination?.id != R.id.fastSearch) {
                        clearBackStack(R.id.fastSearch)
                        navController.navigate(R.id.fastSearch)
                    }
                    true
                }
                R.id.basket -> {
                    if (navController.currentDestination?.id != R.id.basket) {
                        clearBackStack(R.id.basket)
                        navController.navigate(R.id.basket)
                    }
                    true
                }
                R.id.account -> {
                    if (navController.currentDestination?.id != R.id.account) {
                        clearBackStack(R.id.account)
                        navController.navigate(R.id.account)
                    }
                    true
                }
                else -> false
            }
        }
    }
    override fun onAddToCartClicked(dishItem: DishItem) {
        val bundle = Bundle().apply {
            putParcelable("dish", dishItem)
        }
        navController.navigate(R.id.action_global_basket, bundle)
    }
    fun hideMenuMiniFragment() {
        val menuMiniContainer: FrameLayout = findViewById(R.id.menu_mini_container)
        menuMiniContainer.visibility = View.GONE

        val fragment = supportFragmentManager.findFragmentByTag("menuMiniFragment")
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }
    }

    private fun clearBackStack(destinationId: Int) {
        if (navController.currentDestination?.id != destinationId) {
            navController.popBackStack(destinationId, false)
        }
    }
    fun navigateToMenu(categoryId: Int) {
        val bundle = Bundle()
        bundle.putInt("categoryId", categoryId)
        // Исправлено:  action_home_to_menu  ->  action_global_menu
        navController.navigate(R.id.action_home_to_menu, bundle)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        try {
            if (!navController.navigateUp()) {
                super.onBackPressed()
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onBackPressed: ${e.message}")
            super.onBackPressed()
        }
    }
    fun showMenuMiniFragment(dishId: Int, categoryId: Int) {
        findViewById<FrameLayout>(R.id.menu_mini_container).visibility = View.VISIBLE

        val menuMiniFragment = MenuMini().apply {
            arguments = Bundle().apply {
                putInt("dishId", dishId)
                putInt("categoryId", categoryId)
            }
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.menu_mini_container, menuMiniFragment)
            .commit()
    }
}



