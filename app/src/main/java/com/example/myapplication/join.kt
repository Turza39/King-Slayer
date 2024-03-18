package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun join(navController: NavController){
    var invitations by remember { mutableStateOf(message()) }
    fetch { boo-> if (boo != null) { invitations = boo } }
    var accepted by remember { mutableStateOf(false) }
    var start by remember { mutableStateOf("") }
    var txt = "Enter the room"
    val context = LocalContext.current


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
            if(invitations.sender != "" && invitations.sender != FirebaseAuth.getInstance().currentUser?.email){
                Text("You have an invitation as ${invitations.asOpponent}", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(30.dp))
                Row (
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .padding(2.dp)
                        .background(color = Color(255, 230, 255)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ){
                    FloatingActionButton(
                        modifier = Modifier
                            .height(40.dp)
                            .width(70.dp)
                            .padding(5.dp),
                        containerColor = Color.Red,
                        onClick = {
                            update(invitations, false)
                            val myId = FirebaseAuth.getInstance().currentUser?.uid
                            FirebaseDatabase.getInstance().getReference("accounts/$myId/message").removeValue()
                            FirebaseDatabase.getInstance().getReference("accounts/${invitations.senderId}/message").removeValue()
                            navController.navigate("newgame")
                        }) {
                        Text("Decline")
                    }
                    Text(text = invitations.sender)
                    FloatingActionButton(
                        modifier = Modifier
                            .height(40.dp)
                            .width(70.dp)
                            .padding(5.dp),
                        containerColor = Color.Green,
                        onClick = {
                            Stu.uid = invitations.senderId
                            update(invitations, true)
                            accepted = true
                        }) {
                        Text("Accept")
                    }
                }
            }else {
                Text("Sorry you don't have any invitation currently", color = Color.White)
            }

            if(accepted){
                Spacer(modifier = Modifier.height(30.dp))
                FloatingActionButton(modifier = Modifier
                    .height(50.dp)
                    .width(250.dp),
                    containerColor = Color.Green,
                    onClick = {
                        FirebaseDatabase.getInstance().getReference("accounts/${invitations.senderId}/started")
                            .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                start = snapshot.getValue(String::class.java) ?: "false"
                                if(start=="true"){
                                    navController.navigate("players")
                                } else Toast.makeText(context, "Waiting for the owner to get ready", Toast.LENGTH_SHORT).show()
                            }
                            override fun onCancelled(error: DatabaseError) {

                            }
                        })

                    }) {
                    Text(text = txt, fontWeight = FontWeight.Bold, color = Color.Black)
                }

            }
            Spacer(modifier = Modifier.height(30.dp))
                FloatingActionButton(modifier = Modifier
                    .height(50.dp)
                    .width(80.dp),
                    containerColor = Color.Yellow,
                    onClick = { navController.navigate("newgame") }) {
                    Text(text = "Back", fontWeight = FontWeight.Bold, color = Color.Black)
                }
        }
    }

}
fun fetch(onDataFetched: (message?) -> Unit) {
    val myId = FirebaseAuth.getInstance().currentUser?.uid
    val datasetRef = FirebaseDatabase.getInstance().getReference("accounts/$myId/message")

    datasetRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val yourData = snapshot.getValue(message::class.java)
            onDataFetched(yourData)
        }
        override fun onCancelled(error: DatabaseError) {
            onDataFetched(null)
        }
    })
}
fun update(message: message, confirm: Boolean) {
    val myId = FirebaseAuth.getInstance().currentUser?.uid
    val reference = FirebaseDatabase.getInstance().getReference("accounts/$myId/message")

    val newMessage = mapOf(
        "sender" to message.sender,
        "senderId" to message.senderId,
        "reciever" to message.reciever,
        "confirm" to confirm
    )
    reference.updateChildren(newMessage)
        .addOnSuccessListener {}
        .addOnFailureListener {}

    val ref = FirebaseDatabase.getInstance().getReference("accounts/${message.senderId}/message")
    val newMess = mapOf(
        "sender" to message.sender,
        "senderId" to message.senderId,
        "reciever" to message.reciever,
        "confirm" to confirm
    )
    ref.updateChildren(newMess)
        .addOnSuccessListener {}
        .addOnFailureListener {}
}

