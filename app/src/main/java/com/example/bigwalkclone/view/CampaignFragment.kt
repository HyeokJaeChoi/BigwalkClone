package com.example.bigwalkclone.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bigwalkclone.R
import com.example.bigwalkclone.adapter.CampaignAdapter
import com.example.bigwalkclone.adapter.CampaignCategoryAdapter
import com.example.bigwalkclone.adapter.MyCampaignAdapter
import com.example.bigwalkclone.databinding.CampaignFragmentBinding
import com.example.bigwalkclone.decoration.CampaignItemDecoration
import com.example.bigwalkclone.decoration.MyCampaignItemDecoration
import com.example.bigwalkclone.model.CampaignCategoryModel
import com.example.bigwalkclone.model.CampaignModel
import com.example.bigwalkclone.viewmodel.CampaignViewModel
import com.example.bigwalkclone.viewmodel.factory.CampaignViewModelFactory
import kotlinx.coroutines.flow.collectLatest

class CampaignFragment : Fragment() {
    companion object {
        fun newInstance() = CampaignFragment()
    }

    private val campaignViewModel by viewModels<CampaignViewModel> {
        val category = resources.getStringArray(R.array.campaign_category)
        CampaignViewModelFactory(category)
    }
    private var _binding: CampaignFragmentBinding? = null
    private val binding get() = _binding!!
    private val campaignAdapter by lazy { CampaignAdapter() }
    private val myCampaignAdapter by lazy { MyCampaignAdapter() }
    private val campaignCategoryAdapter by lazy {
        CampaignCategoryAdapter() { position ->
            campaignViewModel.selectCategory(position)
        }
    }
    private var campaignSortCondition: Comparator<CampaignModel>? = null
    private var campaignFilterCondition: (suspend (CampaignModel) -> Boolean)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CampaignFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCampaignRecyclerView()
        initMyCampaignRecyclerView()
        initCampaignCategoryAdapter()
        initCampaignSortFilter()
        initCampaignTypeFilter()
        observeCampaignData()
        observeCampaignCategory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initCampaignSortFilter() {
        context?.let { context ->
            ArrayAdapter.createFromResource(context, R.array.campaign_sort, android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.campaignSortFilter.adapter = adapter
                binding.campaignSortFilter.setSelection(Adapter.NO_SELECTION, false)
            }
        }
        binding.campaignSortFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                campaignSortCondition = when(binding.campaignSortFilter.selectedItem.toString()) {
                    getString(R.string.campaign_sort_option_none) -> null
                    getString(R.string.campaign_sort_option_ratio_asc) -> Comparator { value1, value2 ->
                        value1.ratio - value2.ratio
                    }
                    else -> null
                }

                observeCampaignData()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun initCampaignTypeFilter() {
        context?.let { context ->
            ArrayAdapter.createFromResource(context, R.array.campaign_filter, android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.campaignTypeFilter.adapter = adapter
                binding.campaignTypeFilter.setSelection(Adapter.NO_SELECTION, false)
            }
        }
        binding.campaignTypeFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                campaignFilterCondition = when(binding.campaignTypeFilter.selectedItem.toString()) {
                    getString(R.string.campaign_all) -> null
                    getString(R.string.campaign_open) -> { campaign ->
                        campaign.organizations.isEmpty()
                    }
                    getString(R.string.campaign_group) -> { campaign ->
                        campaign.organizations.isNotEmpty()
                    }
                    else -> null
                }

                observeCampaignData()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun initCampaignRecyclerView() {
        binding.listCampaignAll.run {
            layoutManager = LinearLayoutManager(context)
            adapter = campaignAdapter
            addItemDecoration(CampaignItemDecoration())
        }
    }

    private fun initMyCampaignRecyclerView() {
        binding.listMyCampaign.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = myCampaignAdapter
            addItemDecoration(MyCampaignItemDecoration())
        }
    }

    private fun initCampaignCategoryAdapter() {
        binding.listCampaignCategory.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = campaignCategoryAdapter
        }
    }

    private fun observeCampaignData() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            campaignViewModel.getCampaign(sortCondition = campaignSortCondition, filterCondition = campaignFilterCondition).collectLatest { pagingData ->
                campaignAdapter.submitData(pagingData)
            }
        }

        campaignViewModel.myCampaigns.observe(viewLifecycleOwner, { myCampaigns ->
            myCampaignAdapter.submitList(myCampaigns.toList())
        })
    }

    private fun observeCampaignCategory() {
        campaignViewModel.categories.observe(viewLifecycleOwner, {
            campaignCategoryAdapter.submitList(it)
        })
    }

}