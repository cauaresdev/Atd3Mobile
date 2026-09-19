package com.cauarosa.amazonmenutop

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.cauarosa.amazonmenutop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).hide(androidx.core.view.WindowInsetsCompat.Type.statusBars())

        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close
        )
        toggle.drawerArrowDrawable.color = getColor(R.color.meli_dark_blue)

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.title = ""
        binding.navigationDrawer.setNavigationItemSelectedListener(this)
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> openFragment(HomeFragment())
                R.id.bottom_favorites -> openFragment(ProfileFragment()) // Reaproveitando Profile
                R.id.bottom_purchases -> openFragment(CartFragment())    // Reaproveitando Cart
                R.id.bottom_more -> openFragment(MenuFragment())
            }
            true
        }

        binding.fab.setOnClickListener {
            Toast.makeText(this, "Escanear Código QR", Toast.LENGTH_SHORT).show()
        }

        openFragment(HomeFragment())

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    finish()
                }
            }
        })
    }
    override fun onNavigationItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_offers -> openFragment(PrimeFragment())         // Reaproveitando Prime
            R.id.nav_supermarket -> openFragment(EletronicsFragment()) // Reaproveitando Eletronics
            R.id.nav_fashion -> openFragment(FashionFragment())
            R.id.nav_vehicles -> Toast.makeText(this, "Veículos selecionado", Toast.LENGTH_SHORT).show()
            R.id.nav_history -> Toast.makeText(this, "Histórico selecionado", Toast.LENGTH_SHORT).show()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}