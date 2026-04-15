package com.example.bookings_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.bookings_app.ui.theme.BookingsappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookingsappTheme  {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactScreen()
                }
            }
        }
    }
}

fun callPhone(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL, "tel:$phoneNumber".toUri())
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, context.getString(R.string.msgPhone), Toast.LENGTH_SHORT).show()
    }
}

fun sendEmail(context: Context, email: String, subject: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:$email".toUri()
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, context.getString(R.string.msgMail), Toast.LENGTH_SHORT).show()
    }
}

fun showOnMap(context: Context, latitude: Double, longitude: Double, label: String) {
    val geoUri = "geo:$latitude,$longitude($label)".toUri()
    val intent = Intent(Intent.ACTION_VIEW, geoUri)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, context.getString(R.string.msgMap), Toast.LENGTH_SHORT).show()
    }
}

fun shareContact(context: Context, text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    val chooserTitle = context.getString(R.string.chooserTitle)
    val chooser = Intent.createChooser(intent, chooserTitle)
    chooser.putExtra(Intent.EXTRA_TITLE, chooserTitle)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(chooser)
    } else {
        Toast.makeText(context, context.getString(R.string.msgContact), Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ContactScreen() {
    val context = LocalContext.current

    val phoneNumber = context.getString(R.string.phone_number)
    val email = context.getString(R.string.email)
    val emailSubject = context.getString(R.string.email_subject)
    val latitude = 60.0237
    val longitude = 30.2289
    val officeLabel = context.getString(R.string.office_label)
    val myKey = context.getString(R.string.shareTextKey)
    val shareText = "$myKey $phoneNumber, $email"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = context.getString(R.string.contactBook),
            fontSize = 28.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { callPhone(context, phoneNumber) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text( text = context.getString(R.string.callMsg), fontSize = 18.sp)
        }

        Button(
            onClick = { sendEmail(context, email, emailSubject) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = context.getString(R.string.emailMsg), fontSize = 18.sp)
        }

        Button(
            onClick = { showOnMap(context, latitude, longitude, officeLabel) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(context.getString(R.string.mapMsg), fontSize = 18.sp)
        }

        Button(
            onClick = { shareContact(context, shareText) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(context.getString(R.string.contactMsg), fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactScreenPreview() {
    BookingsappTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ContactScreen()
        }
    }
}