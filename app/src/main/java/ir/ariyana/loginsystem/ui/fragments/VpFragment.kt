package ir.ariyana.loginsystem.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ir.ariyana.loginsystem.databinding.FragmentViewPagerBinding
import ir.ariyana.loginsystem.ui.adapter.AdapterViewpager

class VpFragment: Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = AdapterViewpager(parentFragmentManager, lifecycle)
        binding.vpViewPager2.adapter = adapter

        TabLayoutMediator(binding.vpTablayout, binding.vpViewPager2) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "Login"
                }

                1 -> {
                    tab.text = "Register"
                }
            }
        }.attach()
    }
}