package com.example.readingquestsfun.ui.createStory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.ActivityAdminBinding
import com.example.readingquestsfun.viewModels.StoryEditViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityAdminBinding
//    private val _viewModel: StoryEditViewModel by viewModel()

    private val isNew by lazy {
        intent.getBooleanExtra("IS_NEW", true)
    }

    private val _storyId by lazy {
        intent.getStringExtra("STORY_ID")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        setSupportActionBar(_binding.toolbar)
        val toolbar = _binding.toolbar
        toolbar.title

//        _viewModel.getViewModel()

        if(isNew) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_create_story, EditStoryFragment.newInstance(isNew, _storyId))
                .commit()
        }else{
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_create_story, ChaptersFragment.newInstance(_storyId))
                .commit()
        }
    }
}