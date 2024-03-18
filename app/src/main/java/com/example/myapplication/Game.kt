package com.example.myapplication

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.myapplication.Stu.Companion.turn_text
import com.example.myapplication.Stu.Companion.uid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener


class Stu{
    companion object{
        var first_ind: Int =0
        var second_ind: Int =0
        var s_pos: Int =0
        var faka: Int = 0
        var k_jump: Int = 0
        var king: String = ""
        var slayer: String = ""
        var matches: Int = 0
        var uid: String = ""
        var turn_text: String = ""
    }
}
fun move(ob: universal, fst_sla: Boolean, fst_king: Boolean, scnd_sla: Boolean, scnd_king: Boolean, turn: Int): universal {
    var khali = ob.khali.toMutableList()
    var left = ob.left
    var k_pos = ob.k_pos
    var k_succ = ob.k_succ
    var sla_succ = ob.sla_succ
    var k_hungry = ob.k_hungry
    var k_win = ob.k_win
    var sla_win = ob.sla_win
    var invalid = ob.invalid

//    var obj = universal(khali, left, k_pos, k_succ, sla_succ, k_hungry, k_win, sla_win, invalid)

//                  invalid move
    if (fst_sla && scnd_sla || fst_sla && scnd_king){ invalid = true;}
    if(fst_sla && turn%2==1 || fst_king && turn%2==0) { invalid = true; }
//                  slayer's move
    if (fst_sla && !scnd_sla && !scnd_king){
        if (Stu.first_ind == 1) {
            if (Stu.second_ind != 2 && Stu.second_ind != 4) {
                invalid = true
            }
        } else if (Stu.first_ind == 2) {
            if (Stu.second_ind != 1 && Stu.second_ind != 3 && Stu.second_ind != 5) {
                invalid = true
            }
        } else if (Stu.first_ind == 3) {
            if (Stu.second_ind != 2 && Stu.second_ind != 6) {
                invalid = true
            }
        } else if (Stu.first_ind == 4) {
            if (Stu.second_ind != 1 && Stu.second_ind != 5 && Stu.second_ind != 7) {
                invalid = true
            }
        } else if (Stu.first_ind == 5) {
            if (Stu.second_ind != 2 && Stu.second_ind != 4 && Stu.second_ind != 6 && Stu.second_ind != 7) {
                invalid = true
            }
        } else if (Stu.first_ind == 6) {
            if (Stu.second_ind != 3 && Stu.second_ind != 5 && Stu.second_ind != 7) {
                invalid = true
            }
        } else if (Stu.first_ind == 7) {
            if (Stu.second_ind != 4 && Stu.second_ind != 5 && Stu.second_ind != 6 && Stu.second_ind != 8 && Stu.second_ind != 9 && Stu.second_ind != 10) {
                invalid = true
            }
        } else if (Stu.first_ind == 8) {
            if (Stu.second_ind != 7 && Stu.second_ind != 9 && Stu.second_ind != 11) {
                invalid = true
            }
        } else if (Stu.first_ind == 9) {
            if (Stu.second_ind != 7 && Stu.second_ind != 8 && Stu.second_ind != 10 && Stu.second_ind != 12) {
                invalid = true
            }
        } else if (Stu.first_ind == 10) {
            if (Stu.second_ind != 7 && Stu.second_ind != 9 && Stu.second_ind != 13) {
                invalid = true
            }
        } else if (Stu.first_ind == 11) {
            if (Stu.second_ind != 8 && Stu.second_ind != 12) {
                invalid = true
            }
        } else if (Stu.first_ind == 12) {
            if (Stu.second_ind != 9 && Stu.second_ind != 11 && Stu.second_ind != 13) {
                invalid = true
            }
        } else if (Stu.first_ind == 13) {
            if (Stu.second_ind != 10 && Stu.second_ind != 12) {
                invalid = true
            }
        }
        if (!invalid) {
            sla_succ = true
            khali[Stu.second_ind] = false
            khali[Stu.first_ind] = true
            Stu.s_pos = Stu.second_ind
            Stu.faka = Stu.first_ind
        }
    }
//                  king's move
    if (fst_king && !scnd_sla){
        if (Stu.first_ind == 1) {
            if (Stu.second_ind != 2 && Stu.second_ind != 4) {
                invalid = true
            }
        } else if (Stu.first_ind == 2) {
            if (Stu.second_ind != 1 && Stu.second_ind != 3 && Stu.second_ind != 5) {
                invalid = true
            }
        } else if (Stu.first_ind == 3) {
            if (Stu.second_ind != 2 && Stu.second_ind != 6) {
                invalid = true
            }
        } else if (Stu.first_ind == 4) {
            if (Stu.second_ind != 1 && Stu.second_ind != 5 && Stu.second_ind != 7) {
                invalid = true
            }
        } else if (Stu.first_ind == 5) {
            if (Stu.second_ind != 2 && Stu.second_ind != 4 && Stu.second_ind != 6 && Stu.second_ind != 7) {
                invalid = true
            }
        } else if (Stu.first_ind == 6) {
            if (Stu.second_ind != 3 && Stu.second_ind != 5 && Stu.second_ind != 7) {
                invalid = true
            }
        } else if (Stu.first_ind == 7) {
            if (Stu.second_ind != 4 && Stu.second_ind != 5 && Stu.second_ind != 6 && Stu.second_ind != 8 && Stu.second_ind != 9 && Stu.second_ind != 10) {
                invalid = true
            }
        } else if (Stu.first_ind == 8) {
            if (Stu.second_ind != 7 && Stu.second_ind != 9 && Stu.second_ind != 11) {
                invalid = true
            }
        } else if (Stu.first_ind == 9) {
            if (Stu.second_ind != 7 && Stu.second_ind != 8 && Stu.second_ind != 10 && Stu.second_ind != 12) {
                invalid = true
            }
        } else if (Stu.first_ind == 10) {
            if (Stu.second_ind != 7 && Stu.second_ind != 9 && Stu.second_ind != 13) {
                invalid = true
            }
        } else if (Stu.first_ind == 11) {
            if (Stu.second_ind != 8 && Stu.second_ind != 12) {
                invalid = true
            }
        } else if (Stu.first_ind == 12) {
            if (Stu.second_ind != 9 && Stu.second_ind != 11 && Stu.second_ind != 13) {
                invalid = true
            }
        } else if (Stu.first_ind == 13) {
            if (Stu.second_ind != 10 && Stu.second_ind != 12) {
                invalid = true
            }
        }
        if (!invalid) {
            k_succ = true
            khali[Stu.first_ind] = true
            khali[Stu.second_ind] = false
            k_pos = Stu.second_ind
            Stu.faka = Stu.first_ind
        }

    }
//                  hungry king
    if (fst_king && scnd_sla){
        if (Stu.first_ind == 1) {
            if (Stu.second_ind == 2 && khali[3]) {
                k_hungry = true; k_pos = 3; Stu.faka = 1; Stu.s_pos = 2
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[3] = false
            } else if (Stu.second_ind == 4 && khali[7]) {
                k_hungry = true; k_pos = 7; Stu.faka = 1; Stu.s_pos = 4
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[7] = false
            }
        } else if (Stu.first_ind == 2) {
            if (Stu.second_ind == 5 && khali[7]) {
                k_hungry = true; k_pos = 7; Stu.faka = 2; Stu.s_pos = 5
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[7] = false
            }
        } else if (Stu.first_ind == 3) {
            if (Stu.second_ind == 2 && khali[1]) {
                k_hungry = true; k_pos = 1; Stu.faka = 3; Stu.s_pos = 2
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[1] = false
            } else if (Stu.second_ind == 6 && khali[7]) {
                k_hungry = true; k_pos = 7; Stu.faka = 3; Stu.s_pos = 6
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[7] = false
            }
        } else if (Stu.first_ind == 4) {
            if (Stu.second_ind == 5 && khali[6]) {
                k_hungry = true; k_pos = 6; Stu.faka = 4; Stu.s_pos = 5
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[6] = false
            } else if (Stu.second_ind == 7 && khali[10]) {
                k_hungry = true; k_pos = 10; Stu.faka = 4; Stu.s_pos = 7
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[10] = false
            }
        } else if (Stu.first_ind == 5) {
            if (Stu.second_ind == 7 && khali[9]) {
                k_hungry = true;k_pos = 9; Stu.faka = 5; Stu.s_pos = 7
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[9] = false
            }
        } else if (Stu.first_ind == 6) {
            if (Stu.second_ind == 5 && khali[4]) {
                k_hungry = true; k_pos = 4; Stu.faka = 6; Stu.s_pos = 5
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[4] = false
            } else if (Stu.second_ind == 7 && khali[8]) {
                k_hungry = true; k_pos = 8; Stu.faka = 6; Stu.s_pos = 7
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[8] = false
            }
        } else if (Stu.first_ind == 7) {
            if (Stu.second_ind == 4 && khali[1]) {
                k_hungry = true; k_pos = 1; Stu.faka = 7; Stu.s_pos = 4
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[1] = false
            } else if (Stu.second_ind == 5 && khali[2]) {
                k_hungry = true; k_pos = 2; Stu.faka = 7; Stu.s_pos = 5
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[2] = false
            } else if (Stu.second_ind == 6 && khali[3]) {
                k_hungry = true; k_pos = 3; Stu.faka = 7; Stu.s_pos = 6
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[3] = false
            } else if (Stu.second_ind == 8 && khali[11]) {
                k_hungry = true; k_pos = 11; Stu.faka = 7; Stu.s_pos = 8
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[11] = false
            } else if (Stu.second_ind == 9 && khali[12]) {
                k_hungry = true; k_pos = 12; Stu.faka = 7; Stu.s_pos = 9
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[12] = false
            } else if (Stu.second_ind == 10 && khali[13]) {
                k_hungry = true; k_pos = 13; Stu.faka = 7; Stu.s_pos = 10
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[13] = false
            }
        } else if (Stu.first_ind == 8) {
            if (Stu.second_ind == 7 && khali[6]) {
                k_hungry = true; k_pos = 6; Stu.faka = 8; Stu.s_pos = 7
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[6] = false
            } else if (Stu.second_ind == 9 && khali[10]) {
                k_hungry = true; k_pos = 10; Stu.faka = 8; Stu.s_pos = 9
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[10] = false
            }
        } else if (Stu.first_ind == 9) {
            if (Stu.second_ind == 7 && khali[5]) {
                k_hungry = true; k_pos = 5; Stu.faka = 9; Stu.s_pos = 7
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[5] = false
            }
        } else if (Stu.first_ind == 10) {
            if (Stu.second_ind == 7 && khali[4]) {
                k_hungry = true; k_pos = 4; Stu.faka = 10; Stu.s_pos = 7
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[4] = false
            } else if (Stu.second_ind == 9 && khali[8]) {
                k_hungry = true; k_pos = 8; Stu.faka = 10; Stu.s_pos = 9
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[8] = false
            }
        } else if (Stu.first_ind == 11) {
            if (Stu.second_ind == 8 && khali[7]) {
                k_hungry = true; k_pos = 7; Stu.faka = 11; Stu.s_pos = 8
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[7] = false
            } else if (Stu.second_ind == 12 && khali[13]) {
                k_hungry = true; k_pos = 13; Stu.faka = 11; Stu.s_pos = 12
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[13] = false
            }
        } else if (Stu.first_ind == 12) {
            if (Stu.second_ind == 9 && khali[7]) {
                k_hungry = true; k_pos = 7; Stu.faka = 12; Stu.s_pos = 9
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[7] = false
            }
        } else if (Stu.first_ind == 13) {
            if (Stu.second_ind == 10 && khali[7]) {
                k_hungry = true; k_pos = 7; Stu.faka = 13; Stu.s_pos = 10;
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[7] = false
            } else if (Stu.second_ind == 12 && khali[11]) {
                k_hungry = true; k_pos = 11; Stu.faka = 13; Stu.s_pos = 12;
                khali[Stu.first_ind] = true; khali[Stu.second_ind] = true; khali[11] = false
            }
        }
        if (!k_hungry) {
            invalid = true
        }else left--

    }
//                  king safe or not
    if (k_pos == 1) {
        if (!khali[2] && !khali[3] && !khali[4] && !khali[7]){sla_win = true}
    } else if (k_pos == 2) {
        if (khali[1] && !khali[3] && !khali[5] && !khali[7]){sla_win = true}
    } else if (k_pos == 3) {
        if (!khali[1] && !khali[2] && !khali[6] && !khali[7]){sla_win = true}
    } else if (k_pos == 4) {
        if (!khali[1] && !khali[5] && !khali[6] && !khali[7] && !khali[10]){sla_win = true}
    } else if (k_pos == 5) {
        if (!khali[2] && !khali[4] && !khali[6] && !khali[7] && !khali[9]){sla_win = true}
    } else if (k_pos == 6) {
        if (!khali[3] && !khali[4] && !khali[5] && !khali[7] && !khali[8]){sla_win = true}
    } else if (k_pos == 7) {
        if (!khali[1] && !khali[2] && !khali[3] && !khali[4] && !khali[5] && !khali[6] && !khali[8] && !khali[9] && !khali[10] && !khali[11] && !khali[12] && !khali[13]){sla_win = true}
    } else if (k_pos == 8) {
        if (!khali[6] && !khali[7] && !khali[9] && !khali[10] && !khali[11]){sla_win = true}
    } else if (k_pos == 9) {
        if (!khali[5] && !khali[7] && !khali[8] && !khali[10] && !khali[12]){sla_win = true}
    } else if (k_pos == 10) {
        if (!khali[4] && !khali[7] && !khali[8] && !khali[9] && !khali[13]){sla_win = true}
    } else if (k_pos == 11) {
        if (!khali[7] && !khali[8] && !khali[12] && !khali[13]){sla_win = true}
    } else if (k_pos == 12) {
        if (!khali[7] && !khali[9] && !khali[11] && !khali[13]){sla_win = true}
    } else if (k_pos == 13) {
        if (!khali[7] && !khali[10] && !khali[11] && !khali[12]){sla_win = true}
    }

    if(left<4){
        k_win = true
    }
    FirebaseRepository().updateuniversalData(khali.toList(), left, k_pos, k_succ, sla_succ, k_hungry, k_win, sla_win, invalid)
    Stu.first_ind=0; Stu.second_ind=0
    var obj = universal(khali, left, k_pos, k_succ, sla_succ, k_hungry, k_win, sla_win, invalid)
    return obj
}

@Composable
fun WrongMove(){
    val context = LocalContext.current
    Toast.makeText(context, "Wrong move!!", Toast.LENGTH_SHORT).show()
}

@Composable
fun DialogWithImage(
    txt: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    painter: Painter,
    imageDescription: String,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painter,
                    contentDescription = imageDescription,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(160.dp)
                )
                Text(
                    text = txt,
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = {  onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Leave Room")
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Back to Lobby")
                    }
                }
            }
        }
    }
}
fun senderId(callback: (String) -> Unit){
    val myId = FirebaseAuth.getInstance().currentUser?.uid
    val ref = FirebaseDatabase.getInstance().getReference("accounts/$myId/message/senderId")
    ref.addListenerForSingleValueEvent(object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val uid = snapshot.getValue(String ::class.java) ?: ""
            callback(uid)
        }
        override fun onCancelled(error: DatabaseError) {
        }
    })
}
class FirebaseRepository {
    val myId = FirebaseAuth.getInstance().currentUser?.uid
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("users")

    fun getUserData(id: Int, callback: (fData) -> Unit) {
        senderId { bla -> uid = bla }
        val idReference = reference.child("$uid/butt/$id")

        idReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fid = snapshot.child("id").getValue(Int::class.java) ?: 0
                val fking = snapshot.child("king").getValue(Boolean::class.java) ?: false
                val fsla = snapshot.child("sla").getValue(Boolean::class.java) ?: false
                val fkalfa = snapshot.child("kalfa").getValue(Float::class.java) ?: 0f
                val fsalfa = snapshot.child("salfa").getValue(Float::class.java) ?: 0f

                val user = fData(fid, fking, fsla, fkalfa, fsalfa)
                callback(user)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getUniversalData(membr: String, callback: (universal) -> Unit) {
        senderId { bla -> uid = bla }
        val idReference = reference.child("$uid/$membr")

        idReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val khali = snapshot.child("khali").getValue(object : GenericTypeIndicator<List<Boolean>>() {}) ?: emptyList()
                val left = snapshot.child("left").getValue(Int::class.java) ?: 7
                val k_pos = snapshot.child("k_pos").getValue(Int::class.java) ?: 0
                val k_succ = snapshot.child("k_succ").getValue(Boolean::class.java) ?: false
                val sla_succ = snapshot.child("sla_succ").getValue(Boolean::class.java) ?: false
                val k_hungry = snapshot.child("k_hungry").getValue(Boolean::class.java) ?: false
                val k_win = snapshot.child("k_win").getValue(Boolean::class.java) ?: false
                val sla_win = snapshot.child("sla_win").getValue(Boolean::class.java) ?: false
                val invalid = snapshot.child("invalid").getValue(Boolean::class.java) ?: false

                val user = universal(khali, left, k_pos, k_succ, sla_succ, k_hungry, k_win, sla_win, invalid)
                callback(user)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun updateUserData(id: Int, king: Boolean, sla: Boolean, kalfa: Float, salfa: Float) {
        senderId { bla -> uid = bla }
        val childReference = reference.child("$uid/butt/$id")

        val dataToUpdate = mapOf(
            "id" to id,
            "king" to king,
            "sla" to sla,
            "kalfa" to kalfa,
            "salfa" to salfa,
        )
        childReference.updateChildren(dataToUpdate)
            .addOnSuccessListener {}
            .addOnFailureListener {}
    }

    fun updateuniversalData(khali: List<Boolean>, left: Int, k_pos: Int, k_succ: Boolean, sla_succ: Boolean, k_hungry: Boolean,
                            k_win: Boolean, sla_win: Boolean, invalid: Boolean) {
        senderId { bla -> uid = bla }
        val childReference = reference.child("$uid/universal")
        val dataToUpdate = mapOf(
            "khali" to khali,
            "left" to left,
            "k_pos" to k_pos,
            "k_succ" to k_succ,
            "sla_succ" to sla_succ,
            "k_hungry" to k_hungry,
            "k_win" to k_win,
            "sla_win" to sla_win,
            "invalid" to invalid
        )

        childReference.updateChildren(dataToUpdate)
            .addOnSuccessListener {}
            .addOnFailureListener {}
    }
}

@Composable
fun Drawbut(navController: NavController, id: Int, x: Dp, y: Dp, ind: Int, color: MutableList<Color>, k_img: Painter, s_img: Painter){

    // val context = LocalContext.current

    val myRef = FirebaseDatabase.getInstance().getReference("users/$uid/turn")
    var turn by remember { mutableIntStateOf(0) }
    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            turn = dataSnapshot.getValue(Int::class.java)!!
        }
        override fun onCancelled(error: DatabaseError) {
        }
    })

    val myId = FirebaseAuth.getInstance().currentUser?.uid

    var bla by remember { mutableStateOf(universal()) }
    FirebaseRepository().getUniversalData("universal") { yourData ->  bla = yourData }
    val khali: List<Boolean> = bla.khali
    var left = bla.left
    val k_pos = bla.k_pos
    val k_succ = bla.k_succ
    val sla_succ = bla.sla_succ
    val k_hungry = bla.k_hungry
    var k_win = bla.k_win
    var sla_win = bla.sla_win
    var invalid = bla.invalid
    var ob = universal(khali, left, k_pos, k_succ, sla_succ, k_hungry, k_win, sla_win, invalid)

    var currentdt by remember { mutableStateOf(fData()) }
    var fstdt by remember { mutableStateOf(fData()) }
    FirebaseRepository().getUserData(id) { userData -> currentdt = userData }
    FirebaseRepository().getUserData(Stu.first_ind) { userData -> fstdt = userData }

    Button(
        onClick ={
            if(Stu.first_ind==0){
                color[ind]= Color.Yellow;
                Stu.first_ind = ind
                FirebaseRepository().getUserData(Stu.first_ind) { userData -> fstdt = userData }
            }
            else{
                if(Stu.first_ind == ind){
                    Stu.first_ind = 0
                    color[ind] = Color.Green
                }else{
                    color[Stu.first_ind] = Color.Green
                    Stu.second_ind = ind
                    FirebaseRepository().getUserData(ind) { userData -> currentdt = userData }
                    ob = move(ob, fstdt.sla, fstdt.king, currentdt.sla, currentdt.king, turn)
                    myRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) { turn = dataSnapshot.getValue(Int::class.java)!! }
                        override fun onCancelled(error: DatabaseError) {}
                    })

                }
            }

        },
        modifier = Modifier
            .size(60.dp)
            .offset(x - 5.dp, y - 2.dp),
        colors = ButtonDefaults.buttonColors(color[ind])
    ) {
    }

    if(ob.sla_succ) {
//        if(turn%2==0) {
            FirebaseDatabase.getInstance().getReference("users/$uid/turn").setValue(turn + 1)
            FirebaseRepository().updateUserData(Stu.faka, false, false, 0f, 0f)
            FirebaseRepository().updateUserData(Stu.s_pos, false, true, 0f, 1f)
            FirebaseRepository().updateuniversalData(ob.khali.toList(), left, ob.k_pos, ob.k_succ, false, ob.k_hungry, ob.k_win, ob.sla_win, ob.invalid)
//        }else {Toast.makeText(context, "Its King's turn", Toast.LENGTH_SHORT).show()}
        Stu.faka = 0; Stu.s_pos = 0; Stu.k_jump = 0
        ob.sla_succ = false; Stu.turn_text = "King's turn"
    }
    if(ob.k_succ){
//        if(turn%2==1) {
            FirebaseDatabase.getInstance().getReference("users/$uid/turn").setValue(turn + 1)
            FirebaseRepository().updateUserData(Stu.faka, false, false, 0f, 0f)
            FirebaseRepository().updateUserData(ob.k_pos, true, false, 1f, 0f)
            FirebaseRepository().updateuniversalData(ob.khali.toList(), ob.left, ob.k_pos, false, ob.sla_succ, ob.k_hungry, ob.k_win, ob.sla_win, ob.invalid)
//        } else {Toast.makeText(context, "Its Slayer's turn", Toast.LENGTH_SHORT).show()}
        Stu.faka = 0; Stu.s_pos = 0; Stu.k_jump = 0
        ob.k_succ = false; Stu.turn_text = "Slayer's turn"
    }
    if(ob.k_hungry){
//        if(turn%2==1) {
            FirebaseDatabase.getInstance().getReference("users/$uid/turn").setValue(turn + 1)
            FirebaseRepository().updateUserData(ob.k_pos, true, false, 1f, 0f)
            FirebaseRepository().updateUserData(Stu.faka, false, false, 0f, 0f)
            FirebaseRepository().updateUserData(Stu.s_pos, false, false, 0f, 0f)
            FirebaseRepository().updateuniversalData(ob.khali.toList(), ob.left, ob.k_pos, ob.k_succ, ob.sla_succ, false, ob.k_win, ob.sla_win, ob.invalid)
//        } else {Toast.makeText(context, "Its Slayer's turn", Toast.LENGTH_SHORT).show()}
        Stu.faka = 0; Stu.s_pos = 0; Stu.k_jump = 0
        ob.k_hungry = false; Stu.turn_text = "Slayer's turn"
    }

    if(ob.invalid) {
        WrongMove();
        FirebaseRepository().updateuniversalData(khali, left, k_pos, k_succ, sla_succ, k_hungry, k_win, sla_win, false)
    }
    if(ob.k_win){
        Stu.matches=Stu.matches+1
        DialogWithImage(
            txt = "THE KING HAS WON!",
            onDismissRequest = { navController.navigate("home") },
            onConfirmation = { navController.navigate("players") },
            painter = k_img,
            imageDescription = "king image"
        )
        FirebaseDatabase.getInstance().getReference("users/$myId/history/${Stu.matches}").setValue(player(Stu.king, Stu.slayer, Stu.king))
        FirebaseRepository().updateuniversalData(khali.toList(), 7, 9, false, false, false, false, false, false)
        ob.k_win = false

    }else if(ob.sla_win){
        Stu.matches=Stu.matches+1
        DialogWithImage(
            txt = "THE SLAYERS HAVE WON!",
            onDismissRequest = { navController.navigate("home") },
            onConfirmation = { navController.navigate("players") },
            painter = s_img,
            imageDescription = "s image"
        )
        FirebaseDatabase.getInstance().getReference("users/$myId/history/${Stu.matches}").setValue(player(Stu.king, Stu.slayer, Stu.slayer))
        FirebaseRepository().updateuniversalData(khali.toList(), 7, 9, false, false, false, false, false, false)
        ob.sla_win = false

    }

    var turnValue = ""
    if(turn%2==0) turnValue = "Slayer's move"
    else turnValue = "King's move"
    Text(text = turnValue, style = TextStyle(color = Color.White, fontSize = 20.sp), modifier = Modifier.offset(x = 145.dp, y = 60.dp))
    Box(
        modifier = Modifier
            .offset(x, y)
            .size(50.dp)
    ) {
        Image(
            painter = k_img,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .alpha(currentdt.kalfa)
        )
    }
    Box(
        modifier = Modifier
            .offset(x - 5.dp, y)
            .size(50.dp)
    ){
        Image(
            painter = s_img,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .alpha(currentdt.salfa)
        )
    }
}

@Composable
fun Battlefield(navController : NavController) {
//    val context = LocalContext.current
//    Toast.makeText(context, "Slayers will make the first move", Toast.LENGTH_SHORT).show()
    Stu.faka = 0; Stu.s_pos = 0; Stu.first_ind = 0; Stu.second_ind = 0; Stu.k_jump = 0
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
    ) {
        Canvas(modifier = Modifier) {
            drawLine(
                start = Offset(180f, 380f),
                end = Offset(910f, 1770f),
                strokeWidth = 10f,
                color = Color.White
            )
            drawLine(
                start = Offset(910f, 380f),
                end = Offset(180f, 1770f),
                strokeWidth = 10f,
                color = Color.White
            )
            drawLine(
                start = Offset(180f, 390f),
                end = Offset(910f, 390f),
                strokeWidth = 10f,
                color = Color.White
            )
            drawLine(
                start = Offset(180f, 1730f),
                end = Offset(910f, 1730f),
                strokeWidth = 10f,
                color = Color.White
            )
            drawLine(
                start = Offset(350f, 730f),
                end = Offset(710f, 730f),
                strokeWidth = 10f,
                color = Color.White
            )
            drawLine(
                start = Offset(350f, 1390f),
                end = Offset(710f, 1390f),
                strokeWidth = 10f,
                color = Color.White
            )
            drawLine(
                start = Offset(550f, 370f),
                end = Offset(550f, 1750f),
                strokeWidth = 10f,
                color = Color.White
            )
        }

        val butclr = remember { mutableStateListOf(*Array(20) { Color.Green }) }
        val k_img = painterResource(id = R.drawable.crown)
        val s_img = painterResource(id = R.drawable.ninja)
        var khali: List<Boolean> = listOf(false, false, false, false, false, false, false, false, true, false, true, true, true, true)
        FirebaseDatabase.getInstance().getReference("users/$uid/turn").setValue(0)
        val ref = FirebaseDatabase.getInstance().getReference("users/$uid")
        ref.child("universal").setValue(universal(khali, 7, 9, false, false, false, false, false, false,))
        for (i in 1..13) {
            if (i <= 7) {
                ref.child("butt/$i").setValue(fData(i, false, true, 0f, 1f))
            } else if (i == 9){
                ref.child("butt/$i").setValue(fData(i, true, false, 1f, 0f))
            } else {
                ref.child("butt/$i").setValue(fData(i, false, false, 0f, 0f))
            }
        }

        Drawbut(navController, 1, x = 50.dp, y = 115.dp, 1, butclr, k_img = k_img, s_img = s_img)
        Drawbut(navController, 2, x = 175.dp, y = 115.dp, 2, butclr, k_img = k_img, s_img = s_img)
        Drawbut(navController, 3, x = 300.dp, y = 115.dp, 3, butclr, k_img = k_img, s_img = s_img)
        Drawbut(navController, 4, x = 110.dp, y = 235.dp, 4, butclr, k_img = k_img, s_img = s_img)
        Drawbut(navController, 5, x = 175.dp, y = 235.dp, 5, butclr, k_img = k_img, s_img = s_img)
        Drawbut(navController, 6, x = 240.dp, y = 235.dp, 6, butclr, k_img = k_img, s_img = s_img)
        Drawbut(navController, 7, x = 175.dp, y = 360.dp, 7, butclr, k_img = k_img, s_img = s_img)
        Drawbut(navController, 8, x = 110.dp, y = 480.dp, 8, butclr, k_img = k_img, s_img = s_img)
        Drawbut(navController, 9, x = 175.dp, y = 480.dp, 9, butclr, k_img = k_img, s_img = s_img)
        Drawbut(navController, 10, x = 240.dp, y = 480.dp, 10, butclr, k_img = k_img, s_img = s_img)
        Drawbut(navController, 11, x = 50.dp, y = 600.dp, 11, butclr, k_img = k_img, s_img = s_img)
        Drawbut(navController, 12, x = 175.dp, y = 600.dp, 12, butclr, k_img = k_img, s_img = s_img)
        Drawbut(navController, 13, x = 300.dp, y = 600.dp, 13, butclr, k_img = k_img, s_img = s_img)

//        var turn_text by remember{ mutableStateOf("Slayer's turn") }
        Text(text = turn_text, Modifier.background(Color.Green), color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
    }

}

data class fData(
    var id: Int= 0,
    var king: Boolean=false,
    var sla: Boolean=false,
    var kalfa: Float=0f,
    var salfa: Float=0f

)

data class universal(
    var khali: List<Boolean> = listOf(false, false, false, false, false, false, false, false, true, false, true, true, true, true),
    var left: Int = 7,
    var k_pos: Int = 9,
    var k_succ: Boolean = false,
    var sla_succ: Boolean = false,
    var k_hungry: Boolean = false,
    var k_win: Boolean = false,
    var sla_win: Boolean = false,
    var invalid: Boolean = false
)
