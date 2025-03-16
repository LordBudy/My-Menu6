package com.example.mymenu

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
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
                R.id.account
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

        // 4. Обработчик нажатия на кнопку "Назад"
        backButton.setOnClickListener {
            onBackPressed() // самый простой способ
            //navController.navigateUp(appBarConfiguration) // Альтернативный способ с AppBarConfiguration
        }

    }

}