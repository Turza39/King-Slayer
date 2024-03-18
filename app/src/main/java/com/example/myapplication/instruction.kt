package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ins(navController: NavController){
    val instruction ="Well, in the battle field, there will be a King and 7 other assassin who will try to kill the king. " +
            "Each assassin can move to their adjacent spots if it is free. " +
            "The king also can move to at most one   spot at a time, but in a special case, " +
            "if there is a soldier in front of the king and there is a free spot just after the soldier, " +
            "the king can kill the soldier if he wants and move to that free spot with a jump.  " +
            "The king cannot kill a soldier if there isn't any free spot just after that soldier. " +
            "The soldiers will try to confine the king. If there remains no free space for the king to move, " +
            "the soldiers win. And if there remains less than 4 soldiers anytime, the king wins."

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.DarkGray)
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ){
            Text(text = "HOW TO PLAY THIS GAME??", fontSize = 25.sp, color = Color.White)
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = instruction, textAlign = TextAlign.Center, fontSize = 20.sp, modifier = Modifier
                .height(420.dp)
                .fillMaxWidth()
                .background(color = Color.LightGray), fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(20.dp))
            FloatingActionButton(
                modifier = Modifier
                    .height(50.dp)
                    .width(80.dp),
                containerColor = Color.Yellow,
                onClick = { navController.navigate("home")}) {
                Text(text = "Back", fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }
}