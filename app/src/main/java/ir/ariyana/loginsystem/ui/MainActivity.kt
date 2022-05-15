package ir.ariyana.loginsystem.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import ir.ariyana.loginsystem.R
import ir.ariyana.loginsystem.databinding.ActivityMainBinding
import ir.ariyana.loginsystem.ui.adapter.AdapterViewpager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        when(auth.currentUser) {
            null -> {
                binding.mainFragmentContainerView.visibility = View.GONE
                binding.mainViewPager2.visibility = View.VISIBLE
            }

            else -> {
                binding.mainFragmentContainerView.visibility = View.VISIBLE
                binding.mainViewPager2.visibility = View.GONE
            }
        }

        val adapter = AdapterViewpager(supportFragmentManager, lifecycle)
        binding.mainViewPager2.adapter = adapter

        TabLayoutMediator(binding.mainTablayout, binding.mainViewPager2) { tab, position ->
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