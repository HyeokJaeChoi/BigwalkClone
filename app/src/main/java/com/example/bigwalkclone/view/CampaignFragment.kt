package com.example.bigwalkclone.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bigwalkclone.R
import com.example.bigwalkclone.adapter.CampaignAdapter
import com.example.bigwalkclone.adapter.MyCampaignAdapter
import com.example.bigwalkclone.databinding.CampaignFragmentBinding
import com.example.bigwalkclone.decoration.MarginDecoration
import com.example.bigwalkclone.model.CampaignModel
import com.example.bigwalkclone.viewmodel.CampaignViewModel
import kotlinx.coroutines.flow.collectLatest

class CampaignFragment : Fragment() {
    companion object {
        fun newInstance() = CampaignFragment()
    }

    private val campaignViewModel by viewModels<CampaignViewModel>()
    private var _binding: CampaignFragmentBinding? = null
    private val binding get() = _binding!!
    private val campaignAdapter by lazy { CampaignAdapter() }
    private val myCampaignAdapter by lazy { MyCampaignAdapter() }

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
        initCampaignFilter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initCampaignFilter() {
        context?.let { context ->
            ArrayAdapter.createFromResource(context, R.array.campaign_filter, android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.campaignListFilter.adapter = adapter
            }
        }
        binding.campaignListFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                observeCampaignData()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(this@CampaignFragment.javaClass.simpleName, "onNothingSelected")
            }
        }
    }

    private fun initCampaignRecyclerView() {
        binding.listCampaignAll.run {
            layoutManager = LinearLayoutManager(context)
            adapter = campaignAdapter
            addItemDecoration(MarginDecoration())
        }
    }

    private fun initMyCampaignRecyclerView() {
        binding.listMyCampaign.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = myCampaignAdapter
        }
    }

    private fun observeCampaignData() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            val flowToCollect = when(binding.campaignListFilter.selectedItem.toString()) {
                getString(R.string.campaign_all) -> campaignViewModel.campaigns
                getString(R.string.campaign_open) -> campaignViewModel.campaignsOpen
                getString(R.string.campaign_group) -> campaignViewModel.campaignsGroup
                else -> null
            }

            flowToCollect?.let { campaignFlow ->
                campaignFlow.collectLatest {
                    campaignAdapter.submitData(it)
                }
            }
        }

        campaignViewModel.myCampaigns.observe(viewLifecycleOwner, { myCampaigns ->
            myCampaignAdapter.submitList(myCampaigns)
        })
    }

}