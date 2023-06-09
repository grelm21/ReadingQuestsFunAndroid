package com.example.readingquestsfun.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.ActivityReadingBinding
import com.example.readingquestsfun.ui.equipment.EquipmentFragment

class ReadingActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityReadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.btnEquipment.setOnClickListener {
            val fragment = EquipmentFragment()
            supportFragmentManager.beginTransaction().add(R.id.equipment_view, fragment).commit()

            _binding.equipmentView.visibility = View.VISIBLE
        }
    }
}