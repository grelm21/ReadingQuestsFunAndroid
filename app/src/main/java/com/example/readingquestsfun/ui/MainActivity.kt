package com.example.readingquestsfun.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.ActivityMainBinding
import com.example.readingquestsfun.ui.createStory.AdminActivity
import com.example.readingquestsfun.ui.main.CurrentReadingFragment
import com.example.readingquestsfun.ui.main.SearchFragment
import com.example.readingquestsfun.viewModels.CurrentReadingViewModel
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.switchmaterial.SwitchMaterial
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val _viewModel: CurrentReadingViewModel by viewModel()

    private val _navListener by lazy {
        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId){
                R.id.readingFragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_nav_host_fragment, CurrentReadingFragment()).commit()
                    true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_nav_host_fragment, SearchFragment()).commit()
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        setSupportActionBar(_binding.toolbar)
        val toolbar = _binding.toolbar
        toolbar.title = "Привет, ${_viewModel.getUser()}"

        val navController = Navigation.findNavController(this, R.id.main_nav_host_fragment)
        NavigationUI.setupWithNavController(_binding.bottomNavView, navController)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_nav_host_fragment, CurrentReadingFragment()).commit()

        _binding.bottomNavView.setOnItemSelectedListener(_navListener)

        if (_viewModel.getAdminRights() == "ADMIN"){
            _binding.fabCreate.visibility = VISIBLE
            toolbar.findViewById<SwitchMaterial>(R.id.switch_thumb).visibility = VISIBLE
        }else{
            _binding.fabCreate.visibility = GONE
            toolbar.findViewById<SwitchMaterial>(R.id.switch_thumb).visibility = GONE
        }

        _binding.switchThumb.setOnCheckedChangeListener { switch, _ ->
            if (switch.isChecked){
                _binding.fabCreate.visibility = VISIBLE
            }else{
                _binding.fabCreate.visibility = GONE
            }
        }



        _binding.fabCreate.setOnClickListener {
            val intent = Intent(this@MainActivity, AdminActivity::class.java)
            intent.putExtra("IS_NEW", true)
            intent.putExtra("STORY_ID", '0')
            startActivity(intent)
        }
    }
}