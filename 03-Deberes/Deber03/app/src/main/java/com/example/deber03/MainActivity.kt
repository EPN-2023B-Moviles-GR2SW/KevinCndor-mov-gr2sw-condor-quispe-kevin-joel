package com.example.deber03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val inicio: Inicio = Inicio()
    val cartera: Cartera = Cartera()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.inicio -> {
                    loadFragmentos(inicio)
                    true
                }
                R.id.cartera -> {
                    loadFragmentos(cartera)
                    true
                }
                else -> false
            }
        }
        loadFragmentos(inicio)
    }

    fun loadFragmentos(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

}
