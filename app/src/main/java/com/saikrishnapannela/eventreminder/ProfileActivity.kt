package com.saikrishnapannela.eventreminder

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileScreen()
        }
    }
}

@Composable
fun ProfileScreen() {
    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        context.finish()
                    },
                painter = painterResource(id = R.drawable.baseline_arrow_circle_left_36),
                contentDescription = "Reminder Icon"
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "User Profile",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.weight(1f))

        }

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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Name",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.Gray,
                    )
                )

                Text(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    text = ":",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.Black,
                    )
                )

                Text(
                    modifier = Modifier.weight(2f),
                    text = EventReminderAppData.fetchUserName(context),
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.Black,
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Email",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.Gray,
                    )
                )

                Text(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    text = ":",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.Black,
                    )
                )

                Text(
                    modifier = Modifier.weight(2f),
                    text = EventReminderAppData.fetchUserMail(context),
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.Black,
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier,
                text = "Developed by",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray
                )
            )

            Text(
                modifier = Modifier,
                text = "Sai Krishna Pannela",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Gray
                )
            )
        }
    }
}