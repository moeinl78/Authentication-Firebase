package ir.ariyana.loginsystem.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import ir.ariyana.loginsystem.R
import ir.ariyana.loginsystem.databinding.FragmentProfileBinding
import ir.ariyana.loginsystem.utils.CheckConnection

class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()

        binding.fragmentProfileConnectionLottie.repeatCount = LottieDrawable.INFINITE

        if(CheckConnection(binding.root.context).isConnected) {
            binding.fragmentProfileConnectionLottie.visibility = View.GONE
            checkCurrentUser()
        }
        else {
            binding.fragmentProfileConnectionLottie.visibility = View.VISIBLE
            Snackbar
                .make(binding.root.context, requireView(), "Check your internet connection!", Snackbar.LENGTH_SHORT)
                .setAction("Try Again") {

                }
                .show()

        }
    }

    private fun checkCurrentUser() {
        if(auth.currentUser == null) {
            findNavController().navigate(R.id.action_profileFragment_to_vpFragment)
        }
    }
}