package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.KingSlayerTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KingSlayerTheme {
                FirebaseDatabase.getInstance().getReference("accounts/${FirebaseAuth.getInstance().currentUser?.uid}/message").removeValue()
                FirebaseDatabase.getInstance().getReference("accounts/${FirebaseAuth.getInstance().currentUser?.uid}/started").removeValue()
                Stu.uid = ""
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "SignUp" ) {
                    composable("home") { home(navController = navController) }
                    composable("Battlefield"){ Battlefield(navController = navController)}
                    composable("newgame"){ newgame(navController = navController)}
                    composable("history"){ history(navController = navController) }
                    composable("join"){ join(navController = navController)}
                    composable("invite"){ invite(navController = navController)}
                    composable("players"){ players(navController = navController)}
                    composable("ins"){ ins(navController = navController)}
                    composable("SignUp"){ SignupScreen(navController = navController) }
                    composable("LoginScreen"){ LoginScreen(navController = navController) }
                }
            }
        }
    }
}
