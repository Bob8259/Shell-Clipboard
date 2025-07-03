package com.example.input

import android.content.BroadcastReceiver
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.inputmethodservice.InputMethodService
import android.os.Build

class MyInputMethodService : InputMethodService() {

    companion object {
        const val ACTION_READ_CLIPBOARD = "com.example.input.ACTION_READ_CLIPBOARD"
        const val ACTION_SET_CLIPBOARD = "com.example.input.ACTION_SET_CLIPBOARD"
        const val ACTION_CLEAR_CLIPBOARD = "com.example.input.ACTION_CLEAR_CLIPBOARD"
        const val EXTRA_CLIPBOARD_TEXT = "com.example.input.EXTRA_CLIPBOARD_TEXT"
    }

    private val myReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            when (intent?.action) {
                ACTION_READ_CLIPBOARD -> {
                    val clip: ClipData? = clipboard.primaryClip
                    val item = clip?.getItemAt(0)
                    val text = item?.coerceToText(this@MyInputMethodService)?.toString()
                        ?: "Clipboard is empty"
                    setResultData(text)
                    println("Broadcast: Read clipboard, content: $text")
                }

                ACTION_SET_CLIPBOARD -> {
                    val newText = intent.getStringExtra(EXTRA_CLIPBOARD_TEXT)
                    println("Broadcast:  $newText")
                    if (newText != null) {
                        val clipData = ClipData.newPlainText("label", newText)
                        clipboard.setPrimaryClip(clipData)
                        println("Broadcast: Set clipboard to: $newText")
                    } else {
                        println("Broadcast: Set clipboard failed, no text provided.")
                    }
                }

                ACTION_CLEAR_CLIPBOARD -> {
                    val clipData = ClipData.newPlainText("", "")
                    clipboard.setPrimaryClip(clipData)
                    println("Broadcast: Clipboard cleared.")
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter().apply {
            addAction(ACTION_READ_CLIPBOARD)
            addAction(ACTION_SET_CLIPBOARD)
            addAction(ACTION_CLEAR_CLIPBOARD)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(myReceiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("DEPRECATION")
            registerReceiver(myReceiver, filter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myReceiver)
    }
}