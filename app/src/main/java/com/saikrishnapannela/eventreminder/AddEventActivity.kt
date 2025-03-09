package com.saikrishnapannela.eventreminder

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Calendar

class AddEventActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddEventActivityScreen()
        }
    }
}

@Composable
fun AddEventActivityScreen() {

    var eventName by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var eventCategory by remember { mutableStateOf("") }

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
                text = "Event Name",
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

            Text(
                modifier = Modifier,
                text = "Event Category",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Red,
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = eventCategory,
                onValueChange = { eventCategory = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            DateTimePickerRow()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        Toast.makeText(context, "Adding Event", Toast.LENGTH_SHORT).show()
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

@Composable
fun DateTimePickerRow() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // State for date and time
    val dateState = remember { mutableStateOf("") }
    val timeState = remember { mutableStateOf("") }

    // Open Date Picker
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            dateState.value = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Open Time Picker
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            timeState.value = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = dateState.value,
            onValueChange = {},
            label = { Text("Pick Date") },
            readOnly = true,
            modifier = Modifier
                .weight(1f)
                .clickable { datePickerDialog.show() },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Pick Date"
                )
            }
        )

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedTextField(
            value = timeState.value,
            onValueChange = {},
            label = { Text("Pick Time") },
            readOnly = true,
            modifier = Modifier
                .weight(1f)
                .clickable { timePickerDialog.show() },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Pick Time"
                )
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AddEventActivityScreenPreview() {
    AddEventActivityScreen()
}