package com.example.readingquestsfun.ui.equipment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readingquestsfun.R
import com.example.readingquestsfun.adapters.EquipmentAdapter
import com.example.readingquestsfun.databinding.FragmentEquipmentBinding
import com.example.readingquestsfun.viewModels.EquipmentViewModel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class EquipmentFragment : Fragment() {

    private lateinit var _binding: FragmentEquipmentBinding
    private val _equipmentViewModel: EquipmentViewModel by viewModel()
    private val _equipmentAdapter by lazy { EquipmentAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEquipmentBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.equipmentList.adapter = _equipmentAdapter
        _binding.equipmentList.hasFixedSize()

        lifecycleScope.launchWhenCreated {
            _equipmentViewModel.equipment.collect{
                _equipmentAdapter.submitList(it)
            }
        }

        _equipmentViewModel.getEquipment()
    }

}