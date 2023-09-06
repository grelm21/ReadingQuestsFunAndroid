package com.example.readingquestsfun.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentSignupBinding
import com.example.readingquestsfun.ui.MainActivity
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.viewModels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : Fragment() {

    private val _viewModel: LoginViewModel by viewModel()
    private lateinit var _binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignupBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.btnConfirm.setOnClickListener {
            _viewModel.signup(
                _binding.inputLogin.text.toString(),
                _binding.inputPassword.text.toString()
            )
        }

        _viewModel.login.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    _binding.flLoading.visibility = View.GONE
//                    _viewModel.addUserToPref(response.data!!.admin!!, response.data.login)
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }

                is Resource.Error -> {
                    _binding.flLoading.visibility = View.GONE
                    Snackbar.make(requireView(), response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
//                    Snackbar.make(requireView(), "Неверный логин или пароль", Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    _binding.flLoading.visibility = View.VISIBLE
                }
            }
        }
    }
}