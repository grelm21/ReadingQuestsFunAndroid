package com.example.readingquestsfun.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentLoginBinding
import com.example.readingquestsfun.ui.MainActivity
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.viewModels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val _viewModel: LoginViewModel by viewModel()
    private lateinit var _binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.btnConfirm.setOnClickListener {
            _viewModel.login(
                _binding.inputLogin.text.toString(),
                _binding.inputPassword.text.toString()
            )
        }

//        _binding.loginInputLayout

        _viewModel.login.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    _binding.flLoading.visibility = GONE
                    _viewModel.addUserToPref(response.data!![0].admin!!, response.data[0].login)
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }

                is Resource.Error -> {
                    _binding.flLoading.visibility = GONE
                    Snackbar.make(requireView(), response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
//                    Snackbar.make(requireView(), "Неверный логин или пароль", Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    _binding.flLoading.visibility = VISIBLE
                }
            }
        }
    }
}