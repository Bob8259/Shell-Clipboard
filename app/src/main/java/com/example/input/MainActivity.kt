package com.example.input

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.input.ui.theme.InputTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InputTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        UsageInstruction()
                    }
                }
            }
        }
    }
}

@Composable
fun UsageInstruction() {
    val context = LocalContext.current
    val url = "https://github.com/Bob8259/Shell-Clipboard"

    val annotatedString = buildAnnotatedString {
        append("For usage instructions, please visit ")
        pushStringAnnotation(tag = "URL", annotation = url)
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp
            )
        ) {
            append("this link (click here)")
        }
        pop()
        append(".")
    }

    Text(
        text = annotatedString,
        modifier = Modifier.clickable(
            onClick = {
                annotatedString.getStringAnnotations("URL", 0, annotatedString.length)
                    .firstOrNull()?.let { stringAnnotation ->
                        val intent = Intent(Intent.ACTION_VIEW, stringAnnotation.item.toUri())
                        context.startActivity(intent)
                    }
            }
        ),
        style = MaterialTheme.typography.bodyLarge
    )
}



