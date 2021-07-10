package com.example.bigwalkclone.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.bigwalkclone.R
import com.example.bigwalkclone.databinding.CampaignFragmentBinding

class CampaignFragment : Fragment() {
    companion object {
        fun newInstance() = CampaignFragment()
    }

    private val campaignViewModel by viewModels<CampaignViewModel>()
    private var _binding: CampaignFragmentBinding? = null
    private val binding get() = _binding!!

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

}