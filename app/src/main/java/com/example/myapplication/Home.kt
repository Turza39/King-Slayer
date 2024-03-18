package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun home(navController : NavController){
    val currentContext = LocalContext.current
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.DarkGray)
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ){
            FloatingActionButton(
                modifier = Modifier
                    .height(50.dp)
                    .width(170.dp),
                onClick = {
                    navController.navigate("newgame")
                }
            ) {
                Text(text = "NEW GAME", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(20.dp))

            FloatingActionButton(
                modifier = Modifier
                    .height(50.dp)
                    .width(170.dp),
                onClick = { navController.navigate("history") }
            ) {
                Text(text = "History", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(20.dp))

            FloatingActionButton(
                modifier = Modifier
                    .height(50.dp)
                    .width(170.dp),
                onClick = { navController.navigate("ins") }
            ) {
                Text(text = "Instruction", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(20.dp))

            FloatingActionButton(
                modifier = Modifier
                    .height(50.dp)
                    .width(170.dp),
                containerColor = Color.Red,
                onClick = {
                    var auth: FirebaseAuth = FirebaseAuth.getInstance()
                    auth.signOut()
                    navController.navigate("SignUp")
                }
            ) {
                Text(text = "Logout", fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(20.dp))

            FloatingActionButton(
                modifier = Modifier
                    .height(50.dp)
                    .width(170.dp),
                containerColor = Color.Red,
                onClick = { System.exit(0) }
            ) {
                Text(text = "EXIT", fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }
}

