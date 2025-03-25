package eventreminder.by.s3302092saikrishnapannela

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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

    val userEmail = EventReminderAppData.readMail(context)

    var upcomingEvents by remember { mutableStateOf(listOf<AddEventData>()) }
    var isLoading by remember { mutableStateOf(true) }

    var ueCount by remember {
        mutableIntStateOf(0)
    }

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


            if (isLoading) {
                Text(modifier = Modifier.fillMaxSize(), text = "Loading...")
            } else {


                if (upcomingEvents.isNotEmpty()) {
                    LazyColumn {
                        items(upcomingEvents.size) { bookedEvent ->

                            val eventStatusValue = isTodayOrFuture(
                                upcomingEvents[bookedEvent].date,
                                upcomingEvents[bookedEvent].time
                            )

                            if (eventStatusValue) {
                                ueCount++
                                ShowEventItem(upcomingEvents[bookedEvent])
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                    if (ueCount == 0) {
                        ShowEmptyContent()
                    }
                } else {
                    ShowEmptyContent()
                }
            }
        }

    }

}

@Composable
fun ShowEventItem(eventData: AddEventData) {

    val context = LocalContext.current as Activity

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

            Image(
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        shareEvent(context, eventData)
                    },
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = "Share Icon"
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

        val eventStatusValue = isTodayOrFuture(eventData.date, eventData.time)
        var eventStatus = ""

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

        if (eventData.result != "Not Marked") {

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Event Result",
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
                    text = eventData.result,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.Black,
                    )
                )
            }
        } else {

            if (!eventStatusValue) {
                Column(modifier = Modifier.fillMaxWidth()) {


                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Did you complete this work?",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = Color.Red,
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            modifier = Modifier
                                .clickable {
                                    val updatedData = mapOf(
                                        "eventTitle" to eventData.eventTitle,
                                        "description" to eventData.description,
                                        "category" to eventData.category,
                                        "eventName" to eventData.eventName,
                                        "date" to eventData.date,
                                        "time" to eventData.time,
                                        "userMail" to eventData.userMail,
                                        "result" to "Work Done",
                                        "eventId" to eventData.eventId
                                    )

                                    updateEventDetails(
                                        eventData.eventId,
                                        updatedData,
                                        context = context
                                    )
                                },
                            text = "Yes",
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = Color.Blue,
                                textDecoration = TextDecoration.Underline
                            )
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            modifier = Modifier
                                .clickable {

                                    val updatedData = mapOf(
                                        "eventTitle" to eventData.eventTitle,
                                        "description" to eventData.description,
                                        "category" to eventData.category,
                                        "eventName" to eventData.eventName,
                                        "date" to eventData.date,
                                        "time" to eventData.time,
                                        "userMail" to eventData.userMail,
                                        "result" to "Missed",
                                        "eventId" to eventData.eventId
                                    )

                                    updateEventDetails(
                                        eventData.eventId,
                                        updatedData,
                                        context = context
                                    )
                                },
                            text = "No, I Missed",
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = Color.Blue,
                                textDecoration = TextDecoration.Underline
                            )
                        )

                        Spacer(modifier = Modifier.weight(1f))

                    }
                }
            }
        }

    }
}

fun isTodayOrFuture(dateStr: String, timeStr: String): Boolean {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    dateFormat.isLenient = false

    return try {
        val inputDateTime = dateFormat.parse("$dateStr $timeStr") ?: return false
        val currentDateTime = Calendar.getInstance().time

        inputDateTime >= currentDateTime
    } catch (e: Exception) {
        false
    }
}


fun getUpcomingEvents(userMailId: String, callback: (List<AddEventData>) -> Unit) {

    val emailKey = userMailId.replace(".", ",")
    val eventsDBReference = FirebaseDatabase.getInstance().getReference("MyEvents/$emailKey")
    eventsDBReference.addListenerForSingleValueEvent(object : ValueEventListener {
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

fun updateEventDetails(eventId: String, updatedData: Map<String, Any>, context: Context) {


    try {
        val emailKey = EventReminderAppData.readMail(context)
            .replace(".", ",")
        val path = "MyEvents/$emailKey/$eventId"
        Log.e("Test", "Patch Called : $path")
        val eventsDBReference = FirebaseDatabase.getInstance().getReference(path)
        eventsDBReference.updateChildren(updatedData)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Event Updated Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                (context as Activity).finish()
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Failed to update",
                    Toast.LENGTH_SHORT
                ).show()
            }
    } catch (e: Exception) {
        Log.e("Test", "Error Message : ${e.message}")
    }
}


@Composable
fun ShowEmptyContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(id = R.drawable.empty_event),
            contentDescription = "Empty Event"
        )

        Text(
            modifier = Modifier
                .padding(12.dp),
            text = "No Events",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.weight(1f))

    }
}