package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay

@Composable
fun invite(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var clicked by remember { mutableStateOf(true) }
    var inv_butt by remember { mutableStateOf("Send invitation") }
    var invitations by remember { mutableStateOf(message()) }
    val context = LocalContext.current
    var confirm by remember { mutableStateOf(false) }
    var show by remember { mutableStateOf(false) }
    val myId = FirebaseAuth.getInstance().currentUser?.uid
    val items = listOf("King", "Slayer")
    var selected by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp)
            ) { Text(text = "Friend's email", fontSize = 20.sp) }
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
            )

            Spacer(modifier = Modifier.height((10.dp)))
            selected = opponentAs()

            val asOpponent = items[selected]
            Spacer(modifier = Modifier.height(20.dp))
            FloatingActionButton(modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
                containerColor = Color.Green,
                onClick = {
                    selectSpecificUser(email) { id ->
                        if (id != null) {
                            sendInvitation(id, asOpponent)
                            clicked = false
                            inv_butt = "Waiting for the opponent to join.."
                        } else {
                            Toast.makeText(context, "No such account", Toast.LENGTH_SHORT).show()

                        }
                    }

                }) {
                Text(text = inv_butt, fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(20.dp))
            FloatingActionButton(
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp),
                containerColor = Color.Green,
                onClick = {
                    fetch { boo -> if (boo != null) invitations = boo }
                    if (invitations.confirm) {
                        Stu.uid = invitations.senderId
                        FirebaseDatabase.getInstance().getReference("accounts/$myId/started").setValue("true")
                        navController.navigate("players")
                    } else Toast.makeText(context, "Invitation is not accepted yet", Toast.LENGTH_SHORT).show()

                }
            ) {
                Text(text = "Create Room", fontWeight = FontWeight.Bold, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(20.dp))
            FloatingActionButton(
                modifier = Modifier
                    .height(50.dp)
                    .width(80.dp),
                containerColor = Color.Yellow,
                onClick = { navController.navigate("newgame") },
            )
            {
                Text(text = "Back", fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }
}
@Composable
fun opponentAs(): Int {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(0) }
    val items = listOf("King", "Slayer")

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Select opponent as:", color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
                .background(color = Color.White)
                .clickable { expanded = true }
        ) {
            Text(
                text = items[selected],
                modifier = Modifier
                    .padding(16.dp)
                    .padding(end = 8.dp),
                color = Color.Black, fontWeight = FontWeight.Bold
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(200.dp)
                    .background(color = Color.LightGray)
            ) {
                items.forEachIndexed { index, option ->
                    DropdownMenuItem(
                        text = { Text(text = option, color = Color.Black, fontWeight = FontWeight.Bold) },
                        onClick = {
                        selected = index
                        expanded = false
                    })
                }
            }
        }
    }
    return selected
}

fun selectSpecificUser(email: String, callback: (accounts?) -> Unit) {
    val database = FirebaseDatabase.getInstance()
    val accountsRef = database.getReference("accounts")

    accountsRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for (userSnapshot in snapshot.children) {
                val frndEmail = userSnapshot.child("email").getValue(String::class.java)
                val frndId = userSnapshot.child("uid").getValue(String::class.java)
                if (frndEmail == email && frndEmail != FirebaseAuth.getInstance().currentUser?.email) {
                    val user = frndId?.let { accounts(frndEmail, it) }
                    if (user != null) {
                        callback(user)
                        return
                    }
                }
            }
            callback(null)
        }

        override fun onCancelled(error: DatabaseError) {
            callback(null)
        }
    })
}

fun sendInvitation(to: accounts, asOpponent: String) {
    val uid = FirebaseAuth.getInstance().currentUser?.email.toString()
    val messagesRef = FirebaseDatabase.getInstance().getReference("accounts/${to.uid}/message")
    val newMessage = message(
        sender = uid,
        senderId = FirebaseAuth.getInstance().currentUser?.uid.toString(),
        reciever = to.email,
        confirm = false,
        asOpponent = asOpponent
    )
    messagesRef.setValue(newMessage)

    val myId = FirebaseAuth.getInstance().currentUser?.uid
    val messRef = FirebaseDatabase.getInstance().getReference("accounts/$myId/message")
    val newMess = message(
        sender = uid,
        senderId = FirebaseAuth.getInstance().currentUser?.uid.toString(),
        reciever = to.email,
        confirm = false
    )
    messRef.setValue(newMess)
}

@Composable
fun showTost() {
    val context = LocalContext.current
    Toast.makeText(context, "Not ready yet", Toast.LENGTH_SHORT).show()

}

data class message(
    var sender: String = "",
    var senderId: String = "",
    var reciever: String = "",
    var confirm: Boolean = false,
    var asOpponent: String = ""
)
