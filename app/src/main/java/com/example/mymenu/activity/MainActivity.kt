package com.example.mymenu.activity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mymenu.coreData.ApiService.DishDataSource
import com.example.mymenu.coreData.DAO.BasketDao
import com.example.mymenu.coreData.DB.AppDataBase
import com.example.mymenu.basket.data.BasketRepositoryImpl
import com.example.mymenu.basket.domain.GetAllBasketUseCase
import com.example.mymenu.menuMini.presentation.fragment.MenuMini
import com.example.mymenu.basket.presentation.viewModel.BasketViewModel
import com.example.mymenu.basket.presentation.factory.BasketViewModelFactory
import com.example.mymenu.R
import com.google.android.material.bottomnavigation.BottomNavigationView

// @Suppress("DEPRECATION") - подавляем предупреждения о использовании устаревших API
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val MENU_MINI_TAG = "menuMiniFragment"
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var basketViewModel: BasketViewModel
    private lateinit var getAllBasketUseCase: GetAllBasketUseCase
    private lateinit var dishDataSource: DishDataSource
    private lateinit var basketDao: BasketDao
    private lateinit var basketRepository: BasketRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//зависимости
        dishDataSource = DishDataSource()
        basketDao = AppDataBase.getDatabase(applicationContext).basketDao()
        basketRepository = BasketRepositoryImpl(dishDataSource, basketDao)
        getAllBasketUseCase = GetAllBasketUseCase(basketRepository)

// Инициализируем ViewModel, используя фабрику
        basketViewModel =
            ViewModelProvider(this, BasketViewModelFactory(getAllBasketUseCase, basketRepository))
                .get(BasketViewModel::class.java)

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

    fun showSearchResults(query: String) {
        val bundle = Bundle().apply {
            putString("search_query", query)
        }
        navController.navigate(R.id.fastMenu, bundle)
    }

    fun showMenuMiniFragment(dishId: Int, categoryId: Int) {
        val menuMiniFragment = MenuMini(basketViewModel).apply {
            arguments = Bundle().apply {
                putInt("dishId", dishId)
                putInt("categoryId", categoryId)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.Container_frag, menuMiniFragment, MENU_MINI_TAG)
            .addToBackStack(null)
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
        val menuMiniContainer: FrameLayout? = findViewById(R.id.menu_mini_container)
        menuMiniContainer?.visibility = View.GONE

        val fragment = supportFragmentManager.findFragmentByTag(MENU_MINI_TAG)
        fragment?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }
    }
}



