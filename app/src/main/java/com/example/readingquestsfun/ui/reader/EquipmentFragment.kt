package com.example.readingquestsfun.ui.reader

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.core.content.res.ResourcesCompat
import com.example.readingquestsfun.R
import com.example.readingquestsfun.databinding.FragmentEquipmentBinding
import com.example.readingquestsfun.rvadapters.ReservedItemsAdapter
import com.example.readingquestsfun.rvadapters.UserItemsAdapter
import com.example.readingquestsfun.rvadapters.UserStatisticsAdapter
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.viewModels.ReaderViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EquipmentFragment : Fragment() {

    private lateinit var _binding: FragmentEquipmentBinding
    private val _viewModel: ReaderViewModel by activityViewModel()
    private val _itemsAdapter by lazy { UserItemsAdapter() }
    private val _statisticsAdapter by lazy { UserStatisticsAdapter() }
    private val _reservedAdapter by lazy {
        ReservedItemsAdapter { _, position ->
            Log.i("RESERVED_ITEMS_POSITION", position.toString())
            LootDialogFragment.newInstance(position, "RESERVED")
                .show(childFragmentManager, "LootDialog")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEquipmentBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<ImageView>(R.id.menu_button)
            ?.setImageResource(R.drawable.baseline_close_24)

        activity?.findViewById<ImageView>(R.id.menu_button)?.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        _binding.rvItems.adapter = _itemsAdapter
        _binding.rvStatistics.adapter = _statisticsAdapter
        _binding.rvReserved.adapter = _reservedAdapter

        _viewModel.getReservedItems()
        _viewModel.getUserItems()


        /**
         * получаем айтемы из апи, делим их на РВ
         */
        _viewModel.userItems.observe(viewLifecycleOwner) { userItems ->
            when (userItems) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val items = userItems.data!!.filter { it.type != "STATISTICS" }
                    _itemsAdapter.submitList(items.toMutableList())
//                    _itemsAdapter.notifyDataSetChanged()

                    val statistics = userItems.data!!.filter { it.type == "STATISTICS" }
                    _statisticsAdapter.submitList(statistics.toMutableList())
//                    _statisticsAdapter.notifyDataSetChanged()
                }

                is Resource.Error -> {}
                else -> {}
            }
        }

        /**
         * подписываемся на reserved items
         */
        _viewModel.reservedItemsMutable.observe(viewLifecycleOwner) { reservedItems ->
            Log.i("RESERVED_ITEMS", reservedItems.toString())
            _reservedAdapter.submitList(reservedItems.toMutableList())
//            _reservedAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as? ReaderActivity)?.openEquipmentFragment()
    }
}
