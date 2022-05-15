package ir.ariyana.loginsystem.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
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

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.findNavController()

        when(auth.currentUser) {

            null -> {
                navController.navigate(R.id.action_profileFragment_to_vpFragment)
            }

            else -> {
                navController.navigate(R.id.action_vpFragment_to_profileFragment)
            }
        }
    }
}