package com.example.readingquestsfun.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.ActivityReadingBinding
import com.example.readingquestsfun.ui.equipment.EquipmentFragment

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityReadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.btnEquipment.setOnClickListener {

            _binding.equipmentView.visibility = VISIBLE
        }
    }
}