package com.example.bookings_app

import android.content.Context
import android.content.Intent
import android.net.Uri
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

// --- Вспомогательные функции (безопасный запуск Intent) ---

fun callPhone(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Нет приложения для звонков", Toast.LENGTH_SHORT).show()
    }
}

fun sendEmail(context: Context, email: String, subject: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")  // ← адрес прямо в URI
        putExtra(Intent.EXTRA_SUBJECT, subject)
        // EXTRA_EMAIL уже не нужен, т.к. email в URI
    }

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Нет почтового приложения", Toast.LENGTH_SHORT).show()
    }
}

fun showOnMap(context: Context, latitude: Double, longitude: Double, label: String) {
    val geoUri = Uri.parse("geo:0,0?q=$latitude,$longitude($label)")
    val intent = Intent(Intent.ACTION_VIEW, geoUri)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Нет приложения для карт", Toast.LENGTH_SHORT).show()
    }
}

fun shareContact(context: Context, text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    // createChooser – всегда показывает диалог выбора
    val chooser = Intent.createChooser(intent, "Поделиться контактом через...")
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(chooser)
    } else {
        Toast.makeText(context, "Нет приложения для отправки", Toast.LENGTH_SHORT).show()
    }
}

// --- UI на Jetpack Compose ---

@Composable
fun ContactScreen() {
    val context = LocalContext.current

    // Данные контакта
    val phoneNumber = "+7 (495) 123-45-67"
    val email = "contact@example.com"
    val emailSubject = "Обращение"
    val latitude = 60.0237
    val longitude = 30.2289
    val officeLabel = "Наш офис"
    val shareText = "Контакт: $phoneNumber, $email"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        // Заголовок
        Text(
            text = "Контактная книга",
            fontSize = 28.sp,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Кнопка 1: Позвонить
        Button(
            onClick = { callPhone(context, phoneNumber) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Позвонить", fontSize = 18.sp)
        }

        // Кнопка 2: Написать email
        Button(
            onClick = { sendEmail(context, email, emailSubject) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Написать email", fontSize = 18.sp)
        }

        // Кнопка 3: Показать офис на карте
        Button(
            onClick = { showOnMap(context, latitude, longitude, officeLabel) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Показать офис на карте", fontSize = 18.sp)
        }

        // Кнопка 4: Поделиться контактом (с createChooser)
        Button(
            onClick = { shareContact(context, shareText) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Поделиться контактом", fontSize = 18.sp)
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    showSystemUi = true
)
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