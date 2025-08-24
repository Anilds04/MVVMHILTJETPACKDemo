package com.example.mvvmhiltjetpackdemo.utility

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import com.example.mvvmhiltjetpackdemo.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class AuthManager (private val context: Context) {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val credentialManager: CredentialManager = CredentialManager.create(context)

    /**
     * Returns the currently signed-in Firebase user or null if not signed in.
     */
    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    /**
     * Sign in with Google.
     * @param firstTime: true for first-time login, false for subsequent logins.
     * @param nonce: optional, for extra security (recommended if verifying on backend).
     */
    suspend fun signInWithGoogle(firstTime: Boolean = true, nonce: String? = null): FirebaseUser? {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(!firstTime)   // 🔹 false first time, true afterward
            .setServerClientId(BuildConfig.WEB_CLIENT_ID)
            .setAutoSelectEnabled(false)
            .setNonce(nonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )

            when (val credential = result.credential) {
                is CustomCredential -> {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        val googleTokenId = googleIdTokenCredential.idToken
                        val authCredentials = GoogleAuthProvider.getCredential(googleTokenId,null)
                        val authResult = Firebase.auth.signInWithCredential(authCredentials).await()
                        return authResult.user
                    }
                }
            }

        } catch (e: NoCredentialException) {
            Log.d("AuthManager", "No saved Google credentials available")

        } catch (e: Exception) {
            Log.e("AuthManager", "Google Sign-In failed", e)

        }
        return null
    }

    /**
     * Sign out the currently signed-in user.
     */
    fun signOut() {
        firebaseAuth.signOut()
    }

    /**
     * Get a fresh Firebase ID token (optional, for backend verification).
     */
    suspend fun getFreshIdToken(): String? {
        return firebaseAuth.currentUser?.getIdToken(true)?.await()?.token
    }
}