package ir.ariyana.loginsystem.ui.fragments

import android.app.Activity.RESULT_OK
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
import com.google.firebase.ktx.Firebase
import ir.ariyana.loginsystem.R
import ir.ariyana.loginsystem.databinding.FragmentLoginBinding
import ir.ariyana.loginsystem.utils.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class LoginFragment: Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fragmentLoginLottie.repeatCount = LottieDrawable.INFINITE

        auth = FirebaseAuth.getInstance()

        binding.fragmentLoginConfirmButton.setOnClickListener {
            loginUser()
        }

        binding.fragmentLoginGoogleButton.setOnClickListener {
            googleAccountIntent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Constants.GOOGLE_LOGIN_CODE) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data!!).result
            account?.let { acc ->

            }
        }
    }

    // PRIVATE METHODS

    private fun loginUser() {
        val email = binding.fragmentLoginInputUsername.text.toString()
        val password = binding.fragmentLoginInputPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()) {

            CoroutineScope(Dispatchers.IO).launch {

                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkCurrentUser()
                    }
                }
                catch (e: Exception) {
                    Log.e(Constants.EXCEPTION_CONSTANT, e.message!!)
                }
            }
        }
    }

    private fun googleAccountIntent() {
        val options = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Constants.CLIENT_ID)
            .requestEmail()
            .build()

        val signInClient = GoogleSignIn.getClient(binding.root.context, options)
        signInClient.signInIntent.also {
            startActivityForResult(it, Constants.GOOGLE_LOGIN_CODE)
        }
    }

    private fun googleAccountLogin(account: GoogleSignInAccount) {
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
        if(user != null) {
            findNavController().navigate(R.id.action_vpFragment_to_profileFragment)
        }
    }
}