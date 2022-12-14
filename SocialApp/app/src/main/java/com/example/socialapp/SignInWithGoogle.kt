package com.example.socialapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInWithGoogle : AppCompatActivity() {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_with_google)

        auth = Firebase.auth

        fun updateUI(currentUser: FirebaseUser?) {
            if (currentUser==null){
                Log.d(TAG, "Fuck u bitch line 40 ")
                Toast.makeText(this, "lol", Toast.LENGTH_LONG).show()
                return
            }

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        fun onStart() {
            super.onStart()
            // Check if user is signed in (non-null) and update UI accordingly.
            val currentUser = auth.currentUser
            updateUI(currentUser)
        }

        oneTapClient = Identity.getSignInClient(this)
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.default_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, REQ_ONE_TAP,
                        null, 0, 0, 0, null)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(this) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d(TAG, "Fuck u bitch line 88")
                //Log.d(TAG, e.localizedMessage)
            }

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            when (requestCode) {
                REQ_ONE_TAP -> {
                    try {
                        val credential = oneTapClient.getSignInCredentialFromIntent(data)
                        val idToken = credential.googleIdToken
                        when {
                            idToken != null -> {
                                // Got an ID token from Google. Use it to authenticate
                                // with Firebase.
                                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                                auth.signInWithCredential(firebaseCredential)
                                    .addOnCompleteListener(this) { task ->
                                        if (task.isSuccessful) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "signInWithCredential:success")
                                            val user = auth.currentUser
                                            updateUI(user)
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInWithCredential:failure", task.exception)
                                            updateUI(null)
                                        }
                                    }
                                Log.d(TAG, "Got ID token.")
                            }
                            else -> {
                                // Shouldn't happen.
                                Log.d(TAG, "No ID token!")
                            }
                        }
                    } catch (e: ApiException) {
                        Toast.makeText(this,"Machuda sale", Toast.LENGTH_LONG).show()
                    }
                }
            }
            // ...
        }
    }
}