package com.example.myapplication

import android.graphics.Color.rgb
import android.service.autofill.UserData
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LocalPinnableContainer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context
import kotlin.reflect.KProperty

@Composable
fun SignupScreen(navController: NavController){
    val auth: FirebaseAuth = Firebase.auth
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isPasswordVisible by remember { mutableStateOf(false) }

    val currentUser = auth.currentUser
    if (currentUser != null) {
        navController.navigate("home")
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.DarkGray)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ){
            Text(text = "CREATE ACCOUNT", fontSize = 40.sp, color = Color.Magenta)
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Enter email")},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),)
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Lock else Icons.Default.Lock,
                            contentDescription = null
                        )
                    }
                },
            )
            Spacer(modifier = Modifier.height(20.dp))
            FloatingActionButton(modifier = Modifier
                .height(80.dp)
                .width(200.dp)
                .padding(vertical = 16.dp),
//                containerColor = Color.Green,
                onClick = {
                    coroutineScope.launch {
                        if(email.length != 0 && password.length != 0){
                        signUpUser(navController, auth, email, password, context, email)
                        }else Toast.makeText(context, "Please enter valid email and password", Toast.LENGTH_SHORT).show()
                    }
                }) {
                Text(text = "Create", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Already have an account? Sign in!", fontSize = 20.sp, color = Color.White)
            Spacer(modifier = Modifier.height(20.dp))
            FloatingActionButton(modifier = Modifier
                .height(50.dp)
                .width(100.dp),
//                containerColor = Color.Green,
                onClick = { navController.navigate("LoginSCreen")}
            ) {
                Text(text = "Sign in", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    val auth: FirebaseAuth = Firebase.auth
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(255, 230, 255),
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Enter email")},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),)
                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.Lock else Icons.Default.Lock,
                                contentDescription = null
                            )
                        }
                    },
                )
                Button(
                    onClick = {
                        coroutineScope.launch {
                            if(email.length != 0 && password.length != 0){
                                signInUser(navController, auth, email, password, context, email)
                            }else Toast.makeText(context, "Please enter valid email and password", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text("Log In")
                }
            }
        }
    }
}
suspend fun signUpUser(navController: NavController, auth: FirebaseAuth, email: String, password: String, context: android.content.Context, displayName: String) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = FirebaseAuth.getInstance().currentUser?.uid
                FirebaseDatabase.getInstance().getReference("accounts/$uid").setValue(accounts(email, uid.toString()))
                navController.navigate("home")
                Toast.makeText(context, "Successfully Signed Up!", Toast.LENGTH_SHORT).show()
            } else {
                val errorMessage = task.exception?.message ?: "Sign up failed"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
}
suspend fun signInUser(navController: NavController, auth: FirebaseAuth, email: String, password: String, context: android.content.Context, displayName: String) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate("home")
                Toast.makeText(context, "Successfully logged in!", Toast.LENGTH_SHORT).show()
            } else {
                val errorMessage = task.exception?.message ?: "Login failed. No such account"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
}
data class accounts(
    var email: String = "",
    var uid: String = ""
)