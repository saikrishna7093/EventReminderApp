package eventreminder.by.s3302092saikrishnapannela

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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

class CategoriseEventsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CategoriseEventsActivityScreen()
        }
    }
}

@Composable
fun CategoriseEventsActivityScreen() {
    val context = LocalContext.current as Activity

    val userEmail = EventReminderAppData.readMail(context)

    var upcomingEvents by remember { mutableStateOf(listOf<AddEventData>()) }
    var filteredEvents by remember { mutableStateOf(listOf<AddEventData>()) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val categories = listOf<String>(
        "All",
        "Personal Life",
        "Work & Productivity",
        "Finance & Budgeting",
        "Errands & Tasks",
        "Goals & Productivity",
        "Travel & Adventure",
        "Digital & Online Activities"
    )

    LaunchedEffect(userEmail) {
        getUpcomingEvents(userEmail) { events ->
            upcomingEvents = events
            filteredEvents = events
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
                    .clickable { context.finish() },
                painter = painterResource(id = R.drawable.baseline_arrow_circle_left_36),
                contentDescription = "Back Icon"
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier.padding(12.dp),
                text = "Events by Category",
                style = MaterialTheme.typography.headlineSmall.copy(
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

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories.size) { category ->
                    FilterChip(
                        selected = selectedCategory == categories[category],
                        onClick = {
                            selectedCategory =
                                if (selectedCategory == categories[category]) null else categories[category]
                            filteredEvents =
                                if (selectedCategory == null || selectedCategory == "All") {
                                    upcomingEvents
                                } else {
                                    upcomingEvents.filter { it.category == selectedCategory }
                                }
                        },
                        label = {
                            Text(text = categories[category])
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color.Green,
                            containerColor = Color.LightGray
                        )
                    )
                }
            }

            if(isLoading) {
                Text(modifier = Modifier.fillMaxSize(), text = "Loading...")
            }else{

                if(filteredEvents.isNotEmpty()) {
                    LazyColumn {
                        items(filteredEvents.size) { index ->
                            ShowEventItem(filteredEvents[index])
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }else{
                    ShowEmptyContent()
                }
            }
        }
    }
}

fun shareEvent(context: Context,eventData: AddEventData) {
    val shareText = "Event Name : ${eventData.eventName}\nDescription : ${eventData.description}\nEvent Date : ${eventData.date}\nEvent Time : ${eventData.time}"

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(Intent.createChooser(intent, "Share Event via"))
}
