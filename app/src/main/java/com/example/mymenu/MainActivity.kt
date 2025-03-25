package com.example.mymenu

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.mymenu.Presentation.Fragments.MenuMini
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bNav)
        val navController = findNavController(R.id.Container_frag)
        val backButton: ImageButton = findViewById(R.id.backButton)

        // 1. Определяем фрагменты верхнего уровня (те, на которых кнопка "Назад" не нужна)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home,
                R.id.account,
                R.id.menuMini,
                R.id.basket
            )
        )
        bottomNavigationView.setupWithNavController(navController)

        // 3. Слушатель изменений destination (фрагмента)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (appBarConfiguration.topLevelDestinations.contains(destination.id)) {
                // Если это фрагмент верхнего уровня, скрываем кнопку "Назад"
                backButton.visibility = View.GONE
            } else {
                // Иначе показываем кнопку "Назад"
                backButton.visibility = View.VISIBLE
            }
        }
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            // Поиск MenuMiniFragment по тегу
            val menuMiniFragment =
                supportFragmentManager.findFragmentByTag("MenuMiniFragmentTag") as? MenuMini

            // Если MenuMiniFragment отображен, то закрываем его
            if (menuMiniFragment != null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.remove(menuMiniFragment)
                transaction.commit()
                supportFragmentManager.executePendingTransactions()// Добавляем для немедленного выполнения транзакции
            }

            // В зависимости от выбранного пункта меню, переключаемся на нужный фрагмент
            when (item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.home)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.fastSearch -> {
                    navController.navigate(R.id.fastSearch)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.basket -> {
                    navController.navigate(R.id.basket)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.account -> {
                    navController.navigate(R.id.account)
                    return@setOnNavigationItemSelectedListener true
                }

                else -> false
            }
        }

        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}