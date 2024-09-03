package com.app.elenchos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.app.elenchos.presentation.util.Utils
import com.app.elenchos.ui.theme.ElenchosTheme
import java.lang.reflect.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenBlockerContent() {
    Column(
        //modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "This app is blocked. Please complete your goals to access it.")
        /*Button(onClick = { finish() }) {
            Text("Return")
        }*/
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    /*AppTheme {
        ScreenBlockerContent()
    }*/
}

