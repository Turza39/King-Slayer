package com.example.myapplication

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun history(navController: NavController) {
    val myId = FirebaseAuth.getInstance().currentUser?.uid
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("users/$myId/history")
    var data by remember { mutableStateOf(emptyList<player>()) }

    // Use LaunchedEffect to perform the data fetching when the composable is first launched
    LaunchedEffect(true) {
        // Attach a ValueEventListener to retrieve data from Firebase
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList = mutableListOf<player>()
                for (childSnapshot in snapshot.children) {
                    // Assuming the data is stored as a list of FirebaseRowData objects
                    val rowData = childSnapshot.getValue(player::class.java)
                    if (rowData != null) {
                        dataList.add(rowData)
                    }
                }
                data = dataList
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        reference.addValueEventListener(listener)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(2.dp)
                    .background(color = Color.Green),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "King", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(5.dp))
                Text(text = "Slayer", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(5.dp))
                Text(text = "Winner", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(5.dp))
            }
            Spacer(modifier = Modifier.height(3.dp))
            LazyColumn {
                items(data) { triple ->
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .padding(3.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(255, 230, 255),),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = triple.king, modifier = Modifier.weight(1f))
                            Text(text = triple.slayer, modifier = Modifier.weight(1f))
                            Text(text = triple.winner, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}