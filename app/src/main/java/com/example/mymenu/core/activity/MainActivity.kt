package com.example.mymenu.core.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mymenu.core.menu.presentation.fragment.MenuMini
import com.example.mymenu.R
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

        private val MENU_MINI_TAG = "menuMiniFragment"
        private lateinit var bottomNavigationView: BottomNavigationView
        private lateinit var appBarConfiguration: AppBarConfiguration
        private lateinit var navController: NavController

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            bottomNavigationView = findViewById(R.id.bNav)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.Container_frag) as NavHostFragment
            navController = navHostFragment.navController
            bottomNavigationView.setupWithNavController(navController)

            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.home,
                    R.id.account,
                    R.id.menuMini,
                    R.id.basket
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            val backButton: ImageButton = findViewById(R.id.backButton)
            backButton.setOnClickListener {
                onBackPressed()
            }
            navController.addOnDestinationChangedListener { _, destination, _ ->
                backButton.visibility =
                    if (appBarConfiguration.topLevelDestinations.contains(destination.id)) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
                hideMenuMiniFragment()
            }
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home, R.id.fastSearch, R.id.basket, R.id.account -> {
                        navController.navigate(item.itemId)
                        true
                    }

                    else -> false
                }
            }
        }

        fun showSearchResults(query: String, categoryId: Int) {
            val bundle = Bundle().apply {
                putString("search_query", query)
                putInt("category_id", categoryId)
            }
            navController.navigate(R.id.fastSearch, bundle)
        }

    fun showMenuMiniFragment(dishId: Int, categoryId: Int) {
        val menuMiniFragment = MenuMini().apply {
            arguments = Bundle().apply {
                putInt("dishId", dishId)
                putInt("categoryId", categoryId)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.Container_frag, menuMiniFragment, MENU_MINI_TAG)
            .commit()
    }
        override fun onSupportNavigateUp(): Boolean {
            return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        }
        @Deprecated("Deprecated in Java")
        override fun onBackPressed() {
            if (!navController.popBackStack()) {
                super.onBackPressed()
            }
        }

        fun hideMenuMiniFragment() {
            val fragment = supportFragmentManager.findFragmentByTag(MENU_MINI_TAG)
            fragment?.let {
                supportFragmentManager.beginTransaction().remove(it).commit()
            }
        }
    }