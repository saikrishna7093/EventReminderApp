package com.example.eventreminder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GetInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetInScreen()
        }
    }
}

@Composable
fun GetInScreen() {

    var email by remember { mutableStateOf("") }
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
            text = "SIGN IN",
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
                text = "SIGN IN",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier,
                text = "Continue to Sign In",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Red,
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

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
                text = "Sign In",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "Never Booked a Show?",
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.Gray,
                )
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        context.startActivity(Intent(context, GetAccountActivity::class.java))
                        context.finish()
                    },
                text = "Get an account now",
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.Red,
                )
            )


        }

    }

}


@Preview(showBackground = true)
@Composable
fun GetInScreenPreview() {
    GetInScreen()
}