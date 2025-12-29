package com.example.bai1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var mainNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val headerToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(headerToolbar)

        val hostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        mainNavController = hostFragment.navController

        val config = AppBarConfiguration(mainNavController.graph)
        setupActionBarWithNavController(mainNavController, config)
    }

    override fun onSupportNavigateUp(): Boolean {
        return mainNavController.navigateUp() || super.onSupportNavigateUp()
    }
}
