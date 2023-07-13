package com.example.readingquestsfun.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.ActivityLoginBinding
import com.example.readingquestsfun.ui.login.LoginFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val loginFragment = LoginFragment()

        supportFragmentManager.beginTransaction().add(R.id.login_fragment, loginFragment).commit()
    }
}