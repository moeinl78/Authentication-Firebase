package ir.ariyana.loginsystem.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ir.ariyana.loginsystem.ui.fragments.LoginFragment
import ir.ariyana.loginsystem.ui.fragments.RegisterFragment

class AdapterViewpager(private val fragmentManager: FragmentManager, private val lifecycle: Lifecycle):
    FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                LoginFragment()
            }

            1 -> {
                RegisterFragment()
            }

            else -> {
                Fragment()
            }
        }
    }
}