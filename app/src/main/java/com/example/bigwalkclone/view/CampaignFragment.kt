package com.example.bigwalkclone.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bigwalkclone.R
import com.example.bigwalkclone.adapter.CampaignAdapter
import com.example.bigwalkclone.databinding.CampaignFragmentBinding
import com.example.bigwalkclone.viewmodel.CampaignViewModel

class CampaignFragment : Fragment() {
    companion object {
        fun newInstance() = CampaignFragment()
    }

    private val campaignViewModel by viewModels<CampaignViewModel>()
    private var _binding: CampaignFragmentBinding? = null
    private val binding get() = _binding!!
    private val campaignAdapter by lazy { CampaignAdapter() }

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

        initCampaignFilter()
        initCampaignRecyclerView()
        observeCampaignData()
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
    }

    private fun initCampaignRecyclerView() {
        binding.listCampaignAll.run {
            layoutManager = LinearLayoutManager(context)
            adapter = campaignAdapter
        }
    }

    private fun observeCampaignData() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            campaignViewModel.campaigns.observe(viewLifecycleOwner, Observer { pagingData ->
                campaignAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            })
        }
    }

}