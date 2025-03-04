package com.saikrishnapannela.eventreminder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase

class GetAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetAccountScreen()
        }
    }
}

@Composable
fun GetAccountScreen() {

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    val context = LocalContext.current as Activity


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(12.dp),
            text = "REGISTER ACCOUNT",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .background(
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(16.dp)
        ) {

            Text(
                modifier = Modifier,
                text = "GET AN ACCOUNT",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier,
                text = "Continue to register",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Red,
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                modifier = Modifier,
                text = "FULL NAME",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Red,
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = fullName,
                onValueChange = { fullName = it }
            )


            Text(
                modifier = Modifier,
                text = "EMAIL",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Red,
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = email,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                modifier = Modifier,
                text = "CITY",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Red,
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = city,
                onValueChange = { city = it }
            )

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                modifier = Modifier,
                text = "PASSWORD",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Red,
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = password,
                onValueChange = { password = it }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                modifier = Modifier
                    .clickable {
                        when {

                            fullName.isBlank() -> {
                                Toast.makeText(context, "UserName missing", Toast.LENGTH_SHORT)
                                    .show()

                            }

                            email.isBlank() -> {
                                Toast.makeText(context, "EmailId missing", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            city.isBlank() -> {
                                Toast.makeText(context, "city missing", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            password.isBlank() -> {
                                Toast.makeText(context, "Password missing", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            else -> {

                                val eventData = EventData(
                                    fullName,
                                    email,
                                    city,
                                    password

                                )

                                registerEventReminder(eventData, context)

                            }
                        }
                    }
                    .fillMaxWidth()
                    .background(
                        color = colorResource(id = R.color.red),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(vertical = 6.dp),
                text = "Get Now",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "Already Booked a Show?",
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.Gray,
                )
            )

            Text(
                modifier = Modifier
                    .clickable {
                        context.startActivity(Intent(context, GetInActivity::class.java))
                        context.finish()
                    }
                    .align(Alignment.CenterHorizontally),
                text = "Get In Now",
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.Red,
                )
            )


        }

    }

}


fun registerEventReminder(eventData: EventData, context: Context) {

    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("EventReminderData")

    databaseReference.child(eventData.emailId.replace(".", ","))
        .setValue(eventData)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "You Registered Successfully", Toast.LENGTH_SHORT)
                    .show()
//                context.startActivity(Intent(context, CheckInActivity::class.java))
//                (context as Activity).finish()

            } else {
                Toast.makeText(
                    context,
                    "Registration Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { _ ->
            Toast.makeText(
                context,
                "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()
        }
}


@Preview(showBackground = true)
@Composable
fun GetAccountScreenPreview() {
    GetAccountScreen()
}

data class EventData(
    var userName : String = "",
    var emailId : String = "",
    var city : String = "",
    var password: String = ""
)
