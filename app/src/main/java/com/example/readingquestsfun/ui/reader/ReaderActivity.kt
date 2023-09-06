package com.example.readingquestsfun.ui.reader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.ActivityMainBinding
import com.example.readingquestsfun.databinding.ActivityReaderBinding
import com.example.readingquestsfun.viewModels.ReaderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReaderActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityReaderBinding
    private val _viewModel: ReaderViewModel by viewModel()

    private val _storyId by lazy {
        intent.getStringExtra("STORY_ID")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityReaderBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        setSupportActionBar(_binding.toolbar)
        val toolbar = _binding.toolbar
        toolbar.title = "История"

        Log.i("STORY_ID_READER_ACTIVITY", _storyId!!)

        supportFragmentManager.beginTransaction().add(R.id.reader_fragment, StoryFragment.newInstance(_storyId!!)).commit()
    }

    fun openEquipmentFragment(){
        _binding.menuButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(R.id.reader_fragment, EquipmentFragment()).addToBackStack("equip")
                .commit()
        }
        _binding.menuButton.setImageResource(R.drawable.baseline_menu_24)
    }
}