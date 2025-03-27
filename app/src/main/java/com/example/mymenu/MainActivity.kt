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
// @Suppress("DEPRECATION") - подавляем предупреждения о использовании устаревших API
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    // Переопределяем метод onCreate() (вызывается при создании Activity)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Вызываем super.onCreate(), чтобы выполнить базовую инициализацию Activity
        super.onCreate(savedInstanceState)
        // Устанавливаем layout для Activity (activity_main.xml)
        setContentView(R.layout.activity_main)
        // Находим BottomNavigationView по его ID (R.id.bNav)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bNav)
        // Находим NavController (управляет навигацией между фрагментами) в NavHostFragment (R.id.Container_frag)
        val navController = findNavController(R.id.Container_frag)
// Находим ImageButton (кнопка "Назад") по его ID (R.id.backButton)
        val backButton: ImageButton = findViewById(R.id.backButton)

        // 1. Определяем фрагменты каждого уровня (те, на которых кнопка "Назад" не нужна)
        // Создаем AppBarConfiguration, указывая ID фрагментов верхнего уровня (фрагменты, для которых не нужна кнопка "Назад")
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home, // ID фрагмента "Главная"
                R.id.account, // ID фрагмента "Аккаунт"
                R.id.menuMini, // ID фрагмента "Меню мини"
                R.id.basket // ID фрагмента "Корзина"
            )
        )
        // Связываем BottomNavigationView с NavController (чтобы при нажатии на элементы меню происходил переход к соответствующим фрагментам)
        bottomNavigationView.setupWithNavController(navController)

        // 3. Слушатель изменения назначения (фрагмента)
        // Добавляем слушатель изменения назначения (фрагмента) в NavController.
        // Этот слушатель будет вызываться при каждом переходе к новому фрагменту.
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Проверяем, является ли текущий фрагмент фрагментом верхнего уровня (нужно ли скрывать кнопку "Назад")
            if (appBarConfiguration.topLevelDestinations.contains(destination.id)) {
                // если это фрагмент верхнего уровня, скрывает кнопку "Назад"
                // Скрываем кнопку "Назад"
                backButton.visibility = View.GONE
            } else {
                // Иначе пока вызывает кнопку "Назад"
                // Показываем кнопку "Назад"
                backButton.visibility = View.VISIBLE
            }
        }
        // Устанавливаем слушатель нажатий на элементы BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            // Поиск MenuMiniFragment по тегу
            val menuMiniFragment =
                supportFragmentManager.findFragmentByTag("MenuMiniFragmentTag") as? MenuMini

            // Если MenuMiniFragment отображен, то закрываем его
            // Если MenuMiniFragment найден (не null), то закрываем его (удаляем из FragmentManager)
            if (menuMiniFragment != null) {
                // Создаем транзакцию FragmentTransaction для управления фрагментами
                val transaction = supportFragmentManager.beginTransaction()
                // Удаляем MenuMiniFragment из FragmentManager
                transaction.remove(menuMiniFragment)
                // Подтверждаем транзакцию (вносим изменения в FragmentManager)
                transaction.commit()
                // Добавляем для последовательного выполнения передачи
                supportFragmentManager.executePendingTransactions()
            }

            // В зависимости от выбранного пункта меню, переключаемся на нужный фрагмент
            when (item.itemId) {
                // Если выбран элемент "Главная" (R.id.home), то переходим к фрагменту "Главная"
                R.id.home -> {
                    // Переходим к фрагменту "Главная" с помощью NavController
                    navController.navigate(R.id.home)
                    // Возвращаем true, чтобы указать, что мы обработали нажатие на элемент меню
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
// Если выбран элемент "Аккаунт" (R.id.account), то переходим к фрагменту "Аккаунт"
                R.id.account -> {
                    // Переходим к фрагменту "Аккаунт" с помощью NavController
                    navController.navigate(R.id.account)
                    // Возвращаем true, чтобы указать, что мы обработали нажатие на элемент меню
                    return@setOnNavigationItemSelectedListener true
                }
// Если ID элемента меню не распознан, возвращаем false (указываем, что мы не обработали нажатие)
                else -> false
            }
        }
// Устанавливаем слушатель нажатия на кнопку "Назад"
        backButton.setOnClickListener {
            // При нажатии на кнопку "Назад" вызываем метод onBackPressed(), который выполняет
            // переход к предыдущему фрагменту в стеке навигации
            onBackPressed()
        }
    }
}