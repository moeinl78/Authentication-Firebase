package ir.ariyana.loginsystem.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import ir.ariyana.loginsystem.R
import ir.ariyana.loginsystem.databinding.FragmentRegisterBinding
import ir.ariyana.loginsystem.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegisterFragment: Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        binding.fragmentRegisterLottie.repeatCount = LottieDrawable.INFINITE

        binding.fragmentRegisterConfirmButton.setOnClickListener {
            createAccount()
        }

        binding.fragmentRegisterGoogleButton.setOnClickListener {
            googleAccountIntent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.GOOGLE_REGISTER_CODE) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data!!).result
            account?.let { acc ->
                googleAccountRegister(acc)
            }
        }
    }

    private fun createAccount() {
        val username = binding.fragmentRegisterInputUsername.text.toString()
        val email = binding.fragmentRegisterInputEmail.text.toString()
        val password = binding.fragmentRegisterInputPassword.text.toString()

        val update = UserProfileChangeRequest
            .Builder()
            .setDisplayName(username)
            .build()

        CoroutineScope(Dispatchers.IO).launch {

            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                auth.currentUser?.updateProfile(update)
                withContext(Dispatchers.Main) {
                    checkCurrentUser()
                }
            }
            catch (e: Exception) {
                Log.e(Constants.EXCEPTION_CONSTANT, e.message!!)
            }
        }
    }

    private fun googleAccountIntent() {
        val options = GoogleSignInOptions
            .Builder()
            .requestIdToken(Constants.CLIENT_ID)
            .requestEmail()
            .build()

        val signInClient = GoogleSignIn.getClient(binding.root.context, options)
        signInClient.signInIntent.also {
            startActivityForResult(it, Constants.GOOGLE_REGISTER_CODE)
        }
    }

    private fun googleAccountRegister(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main) {
                    checkCurrentUser()
                }
            }
            catch (e: Exception) {
                Log.e(Constants.EXCEPTION_CONSTANT, e.message!!)
            }
        }
    }

    private fun checkCurrentUser() {
        val user = auth.currentUser
        if (user == null) {
            findNavController().navigate(R.id.action_vpFragment_to_profileFragment)
        }
    }
}