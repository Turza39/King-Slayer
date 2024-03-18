package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

data class player(
        var king: String = "",
        var slayer: String = "",
        var winner: String = ""
)
@Composable
fun players(navController: NavController){
    var king by remember{ mutableStateOf("") }
    var slayer by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.DarkGray)
        ){
            Text(text = "Enter king's name", fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = king,
                onValueChange = { king = it },
                label = { Text("King's name:") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = "Enter assassin squad's name:", fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = slayer,
                onValueChange = { slayer = it },
                label = { Text("Slayer's name:")},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),)
            Spacer(modifier = Modifier.height(30.dp))
            FloatingActionButton(modifier = Modifier
                .height(50.dp)
                .width(80.dp),
                containerColor = Color.Green,
                onClick = {
                    Stu.king = king; Stu.slayer = slayer;
                    val myId = FirebaseAuth.getInstance().currentUser?.uid
//                    FirebaseDatabase.getInstance().getReference("accounts/$myId/started").setValue("true")
                    navController.navigate("Battlefield")
                }) {
                Text(text = "Start", fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(20.dp))
            FloatingActionButton(modifier = Modifier
                .height(50.dp)
                .width(80.dp),
                containerColor = Color.Yellow,
                onClick = { navController.navigate("invite") }) {
                Text(text = "Back", fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }
}
class FirebaseRepo {

    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("butt")

    fun getHistory(callback: (player) -> Unit) {
        val idReference = reference.child("history")

        idReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val king = snapshot.child("king").getValue(String::class.java) ?: ""
                val slayer = snapshot.child("slayer").getValue(String::class.java) ?: ""
                val winner = snapshot.child("winner").getValue(String::class.java) ?: ""
                val user = player(king, slayer, winner)
                callback(user)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun updateHistory(king: String, slayer: String, winner: String) {
        val childReference = reference.child("history")
        val dataToUpdate = mapOf(
            "king" to king,
            "slayer" to slayer,
            "winner" to winner
        )
        childReference.updateChildren(dataToUpdate)
            .addOnSuccessListener {}
            .addOnFailureListener {}
    }
}
