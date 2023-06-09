package com.example.readingquestsfun.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.example.readingquestsfun.models.EquipmentModel
import com.example.readingquestsfun.repository.EquipmentRepository

class EquipmentViewModel(private val _repo: EquipmentRepository): ViewModel() {

    private val _equipmentSet = MutableLiveData<List<EquipmentModel>>()
    val equipment = _equipmentSet.asFlow()

    fun getEquipment(){
        _equipmentSet.value = _repo.createEquipmentSet()
    }
}