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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpcomingEventsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UpcomingEventsActivityScreen()
        }
    }
}

@Composable
fun UpcomingEventsActivityScreen() {
    val context = LocalContext.current as Activity

    val userEmail = EventReminderAppData.fetchUserMail(context)

    var upcomingEvents by remember { mutableStateOf(listOf<AddEventData>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(userEmail) {
        getUpcomingEvents(userEmail) { orders ->
            upcomingEvents = orders
            isLoading = false
        }
    }

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
                text = "Upcoming Events",
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
                .padding(16.dp)
        ) {


            LazyColumn {
                items(upcomingEvents.size) { bookedEvent ->

                    val eventStatusValue = isTodayOrFuture(upcomingEvents[bookedEvent].date)

                    if (eventStatusValue) {
                        ShowEventItem(upcomingEvents[bookedEvent])
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

    }

}

@Composable
fun ShowEventItem(eventData: AddEventData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.black),
                shape = RoundedCornerShape(2.dp)
            )
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Event Title",
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
                modifier = Modifier.weight(1f),
                text = eventData.eventTitle,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.Black,
                )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Event Description",
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
                modifier = Modifier.weight(1f),
                text = eventData.description,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.Black,
                )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Event Category",
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
                modifier = Modifier.weight(1f),
                text = eventData.category,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.Black,
                )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Event Name",
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
                modifier = Modifier.weight(1f),
                text = eventData.eventName,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.Black,
                )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Event Date",
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
                modifier = Modifier.weight(1f),
                text = eventData.date,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.Black,
                )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Event Time",
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
                modifier = Modifier.weight(1f),
                text = eventData.time,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.Black,
                )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Event Status",
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

            val eventStatusValue = isTodayOrFuture(eventData.date)

            var eventStatus = ""

            if (eventStatusValue) {
                eventStatus = "UpComing"
            } else {
                eventStatus = "Completed"
            }

            Text(
                modifier = Modifier.weight(1f),
                text = eventStatus,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.Black,
                )
            )
        }

    }
}

fun isTodayOrFuture(dateStr: String): Boolean {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    dateFormat.isLenient = false // Ensures strict date parsing

    return try {
        val inputDate = dateFormat.parse(dateStr) ?: return false
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        inputDate >= today
    } catch (e: Exception) {
        false // Return false if parsing fails (invalid date format)
    }
}

fun getUpcomingEvents(userMailId: String, callback: (List<AddEventData>) -> Unit) {

    val emailKey = userMailId.replace(".", ",")

    val databaseReference = FirebaseDatabase.getInstance().getReference("MyEvents/$emailKey")

    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val eventsList = mutableListOf<AddEventData>()
            for (eventSnapShot in snapshot.children) {
                val event = eventSnapShot.getValue(AddEventData::class.java)
                event?.let { eventsList.add(it) }
            }

            callback(eventsList)
        }

        override fun onCancelled(error: DatabaseError) {
            println("Error: ${error.message}")
            callback(emptyList())
        }
    })
}