package com.example.readingquestsfun.ui.reader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.ActivityMainBinding
import com.example.readingquestsfun.databinding.ActivityReaderBinding
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.viewModels.ReaderViewModel
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ReaderActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityReaderBinding
    private val _viewModel: ReaderViewModel by viewModel {
        parametersOf(_storyId)
    }
    private lateinit var _toolbar: MaterialToolbar

    private val _storyId by lazy {
        intent.getStringExtra("STORY_ID")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityReaderBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        setSupportActionBar(_binding.toolbar)
        _toolbar = _binding.toolbar

        supportFragmentManager.beginTransaction().add(R.id.reader_fragment, StoryFragment.newInstance(_storyId!!)).commit()

        _viewModel.story.observe(this){story ->
            when(story){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _toolbar.title = story.data!!.title
                }
                is Resource.Error -> {}
            }
        }
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