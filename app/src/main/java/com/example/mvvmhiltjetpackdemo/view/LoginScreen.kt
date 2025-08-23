package com.example.mvvmhiltjetpackdemo.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import com.example.mvvmhiltjetpackdemo.BuildConfig
import com.example.mvvmhiltjetpackdemo.R
import com.example.mvvmhiltjetpackdemo.utility.AuthManager

import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun LoginScreen(modifier: Modifier, loginSuccess: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_shopping),
            contentDescription = "Image"
        )
        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Welcome back", fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(text = "Login to your account")

        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it }, label = { Text(text = "Email") })

        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(onClick = {
            loginSuccess()
        }) {
            Text(text = "LOGIN")
        }

        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "Forgot Password?", modifier = Modifier.clickable { })
        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "Or sign in with")



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            GoogleLoginButton(
                onSignInError = { },
                onSignInSuccess = {
                    Log.d("TAG", "LoginScreen: ${it.email}")
                    Log.d("TAG", "LoginScreen: ${it.displayName}")
                    loginSuccess()
                })

            Image(
                painter = painterResource(R.drawable.communication),
                "",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {

                    }
            )
            Image(
                painter = painterResource(R.drawable.twitter),
                "",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {

                    }
            )
        }
    }
}

@Composable
fun TextBox() {

    TextField(
        value = "", onValueChange = { },
        label = { Text(text = "Name") },
        placeholder = { Text(text = "NIL") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(modifier = Modifier, {})
}


@Composable
fun GoogleLoginButton(
    onSignInSuccess: (user: FirebaseUser) -> Unit,
    onSignInError: (Throwable) -> Unit
) {
    val context = LocalContext.current
    val authManager = remember { AuthManager(context) }
    val coroutineScope = rememberCoroutineScope()

    Image(
        painter = painterResource(R.drawable.google),
        contentDescription = "Google Sign-In",
        modifier = Modifier
            .size(40.dp)
            .clickable {

                coroutineScope.launch {
                    val user =
                        authManager.signInWithGoogle(firstTime = true)  // first-time login
                    if (user != null) {
                        onSignInSuccess(user)
                    } else {
                        Toast.makeText(context, "Google Sign-In failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }


            }
    )
}
