package eventreminder.by.s3302092saikrishnapannela

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeActivityScreen()
        }
    }
}

@Composable
fun HomeActivityScreen() {

    val context = LocalContext.current as Activity


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(12.dp),
            text = "Events DashBoard",
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

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.black),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 0.dp, vertical = 12.dp)
                        .clickable {
                            context.startActivity(
                                Intent(
                                    context,
                                    UpcomingEventsActivity::class.java
                                )
                            )
                        }
                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.upcoming_event),
                        contentDescription = "Reminder Icon"
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = "Upcoming Events",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.black),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 0.dp, vertical = 12.dp)
                        .clickable {
                            context.startActivity(
                                Intent(
                                    context,
                                    CategoriseEventsActivity::class.java
                                )
                            )

                        }
                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.categories_event),
                        contentDescription = "Reminder Icon"
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = "Categorised Events",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .clickable {
                            context.startActivity(Intent(context, AddEventActivity::class.java))
                        }
                        .weight(1f)
                        .wrapContentHeight()
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.black),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 0.dp, vertical = 12.dp)


                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.add_event),
                        contentDescription = "Reminder Icon"
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = "Add Event",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier
                        .clickable {
                            context.startActivity(Intent(context, ProfileActivity::class.java))
                        }
                        .weight(1f)
                        .wrapContentHeight()
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.black),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 0.dp, vertical = 12.dp)

                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.user_profile),
                        contentDescription = "Reminder Icon"
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = "Profile",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        EventReminderAppData.writeLS(context, false)
                        context.startActivity(Intent(context, GetInActivity::class.java))
                        context.finish()

                        Toast.makeText(context, "Logout Successfully", Toast.LENGTH_SHORT).show()

                    }
                    .background(
                        color = colorResource(id = R.color.red),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.red),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(vertical = 6.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Logout",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

        }

    }
}


@Preview(showBackground = true)
@Composable
fun HomeActivityScreenPreview() {
    HomeActivityScreen()
}