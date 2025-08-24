package com.example.mvvmhiltjetpackdemo.view

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvmhiltjetpackdemo.utility.AppDataStore
import com.example.mvvmhiltjetpackdemo.utility.AuthManager
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@Composable
fun SettingScreen(modifier: Modifier, onLogout : () -> Unit) {

    val context = LocalContext.current
    val user by getUser(context).collectAsState(initial = UserProfile())

    val scope = rememberCoroutineScope()
    val authManager = remember { AuthManager(context) }

    Column(modifier = modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Log.d("TAG", "SettingScreen: ${user.photoUrl}")

            GlideImage(
                imageModel = { user.photoUrl },
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop
                ),
                loading = {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                },
                failure = {
                    Text("Image failed to load", modifier = Modifier.padding(16.dp))
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Name - bold and larger
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp)) // small spacing

                // Email - lighter, smaller
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Optional Edit or Arrow
            // Optional Edit Icon
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp) // space above and horizontal
                .height(1.dp)
                .background(Color.Gray)
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    scope.launch {
                        authManager.signOut()
                        clearUser(context)
                        onLogout()
                    }
                }
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout, // built-in logout icon
                contentDescription = "Logout",
                tint = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.width(8.dp)) // spacing between icon and text

            Text(
                text = "Logout",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


fun getUser(context: Context): Flow<UserProfile> {
    val nameFlow = AppDataStore.getString(context, "user_name")
    val emailFlow = AppDataStore.getString(context, "user_email")
    val photoFlow = AppDataStore.getString(context, "user_photo")

    return combine(nameFlow, emailFlow, photoFlow) { name, email, photo ->
        UserProfile(name, email, photo)
    }
}


data class UserProfile(
    val name: String = "",
    val email: String = "",
    val photoUrl: String = ""
)

suspend  fun clearUser(context: Context) {
    AppDataStore.clear(context)
}