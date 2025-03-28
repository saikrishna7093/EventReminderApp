package eventreminder.by.s3302092saikrishnapannela

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class AddEventActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                        100
                    )
                }
            }

            AddEventActivityScreen()
        }
    }
}

@Composable
fun AddEventActivityScreen() {

    val categories = mapOf(
        "Personal Life" to listOf("Workout", "Yoga", "Reading", "Birthday", "Gaming"),
        "Work & Productivity" to listOf("Meeting", "Project Deadline", "Online Course"),
        "Finance & Budgeting" to listOf("Salary", "Bills", "Investments"),
        "Errands & Tasks" to listOf("Groceries", "Doctor Appointment", "Home Cleaning"),
        "Goals & Productivity" to listOf("Daily Routine", "Savings Target", "Skill Learning"),
        "Travel & Adventure" to listOf("Weekend Trip", "Flight Booking", "New Restaurant"),
        "Digital & Online Activities" to listOf(
            "Social Media Post",
            "Online Shopping",
            "App Updates"
        )
    )


    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedEvent by remember { mutableStateOf<String?>(null) }
    var expandedCategory by remember { mutableStateOf(false) }
    var expandedEvent by remember { mutableStateOf(false) }


    var eventName by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var eventCategory by remember { mutableStateOf("") }

    var dateState by remember { mutableStateOf("") }
    var timeState by remember { mutableStateOf("") }

    val context = LocalContext.current as Activity

    val calendar = Calendar.getInstance()


    var selYear by remember { mutableIntStateOf(0) }
    var selMonth by remember { mutableIntStateOf(0) }
    var selDOM by remember { mutableIntStateOf(0) }
    var selHOD by remember { mutableIntStateOf(0) }
    var selMinute by remember { mutableIntStateOf(0) }

    var isChecked by remember { mutableStateOf(false) }


    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selYear = year
            selMonth = month
            selDOM = dayOfMonth


            Log.e("Test", "Y - $year , M - ${month + 1} , DOM - $dayOfMonth")

            dateState = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Open Time Picker
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->

            selHOD = hourOfDay
            selMinute = minute



            Log.e("Test", "HOD - $hourOfDay , Minute - $minute")

            timeState = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )

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
                text = "Add Event",
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

            Text(
                modifier = Modifier,
                text = "Event Title",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Red,
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = eventName,
                onValueChange = { eventName = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                modifier = Modifier,
                text = "Event Description",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Red,
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = eventDescription,
                minLines = 3,
                onValueChange = { eventDescription = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Event Category Dropdown
                Text(
                    "Select Event Category", style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Red,
                    )
                )
                Box {
                    OutlinedTextField(
                        value = selectedCategory ?: "",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Icon",
                                Modifier.clickable { expandedCategory = true })
                        }
                    )
                    DropdownMenu(
                        expanded = expandedCategory,
                        onDismissRequest = { expandedCategory = false }) {
                        categories.keys.forEach { category ->
                            DropdownMenuItem(text = { Text(category) }, onClick = {
                                selectedCategory = category
                                selectedEvent = null // Reset event selection when category changes
                                expandedCategory = false
                            })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Select Event Name", style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Red,
                    )
                )
                Box {
                    OutlinedTextField(
                        value = selectedEvent ?: "",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Icon",
                                Modifier.clickable { expandedEvent = true })
                        }
                    )
                    DropdownMenu(
                        expanded = expandedEvent,
                        onDismissRequest = { expandedEvent = false }) {
                        categories[selectedCategory]?.forEach { event ->
                            DropdownMenuItem(text = { Text(event) }, onClick = {
                                selectedEvent = event
                                expandedEvent = false
                            })
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                        .height(50.dp)
                        .clickable {
                            // Handle the click event, e.g., show a date picker
                        }
                        .background(Color.LightGray, MaterialTheme.shapes.medium)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = dateState.ifEmpty { "Event Date" },
                        color = if (dateState.isEmpty()) Color.Gray else Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.DateRange, // Replace with your desired icon
                        contentDescription = "Calendar Icon",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(24.dp)
                            .clickable {
                                datePickerDialog.show()
                            },
                        tint = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                        .height(50.dp)
                        .clickable {
                            // Handle the click event, e.g., show a date picker
                        }
                        .background(Color.LightGray, MaterialTheme.shapes.medium)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = timeState.ifEmpty { "Event Time" },
                        color = if (timeState.isEmpty()) Color.Gray else Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.DateRange, // Replace with your desired icon
                        contentDescription = "Calendar Icon",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(24.dp)
                            .clickable {
                                timePickerDialog.show()
                            },
                        tint = Color.DarkGray
                    )
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                )
                Text(
                    text = "Notify me about this event",
                    modifier = Modifier.padding(start = 8.dp)
                )

            }


            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val addEventData = AddEventData(
                            eventName,
                            eventDescription,
                            selectedCategory!!,
                            selectedEvent!!,
                            dateState,
                            timeState
                        )


                        val testCalendar = Calendar
                            .getInstance()
                            .apply {
                                set(Calendar.YEAR, selYear)
                                set(Calendar.MONTH, selMonth)
                                set(Calendar.DAY_OF_MONTH, selDOM)
                                set(Calendar.HOUR_OF_DAY, selHOD)
                                set(Calendar.MINUTE, selMinute)
                                set(Calendar.SECOND, 0)
                            }

                        if (testCalendar.timeInMillis > System.currentTimeMillis()) {

                            if (isChecked)
                                scheduleNotification(
                                    context,
                                    testCalendar.timeInMillis,
                                    eventName,
                                    eventDescription
                                )



                            AddEvent(addEventData, context)

                        } else {
                            Toast
                                .makeText(
                                    context,
                                    "Please select a future date and time",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        }

                    }
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
                text = "Save Event",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                )
            )
        }

    }
}


private fun AddEvent(eventData: AddEventData, activityContext: Context) {

    val fireDB = FirebaseDatabase.getInstance()
    val eventsDBReference = fireDB.getReference("MyEvents")
    val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    val eventId = dateFormat.format(Date())
    eventData.eventId = eventId
    val userEmail = EventReminderAppData.readMail(activityContext)
    eventsDBReference.child(userEmail.replace(".", ",")).child(eventId).setValue(eventData)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activityContext, "Event Added Successfully", Toast.LENGTH_SHORT)
                    .show()
                (activityContext as Activity).finish()
            } else {
                Toast.makeText(
                    activityContext,
                    "User Registration Failed: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(
                activityContext,
                "User Registration Failed: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
}


@Preview(showBackground = true)
@Composable
fun AddEventActivityScreenPreview() {
    AddEventActivityScreen()
}

fun scheduleNotification(context: Context, timeInMillis: Long, title: String, message: String) {
    NotificationScheduler.scheduleNotification(
        context = context,
        title = title,
        message = message,
        timeInMillis = timeInMillis
    )
}