package com.example.input

import android.content.BroadcastReceiver
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent

class ClipboardReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        when (intent.action) {
            MyInputMethodService.ACTION_READ_CLIPBOARD -> {
                val clip: ClipData? = clipboard.primaryClip
                val item = clip?.getItemAt(0)
                val text = item?.coerceToText(context)?.toString() ?: ""
                setResultData(text)
            }

            MyInputMethodService.ACTION_SET_CLIPBOARD -> {
                val newText = intent.getStringExtra(MyInputMethodService.EXTRA_CLIPBOARD_TEXT)
                if (newText != null) {
                    clipboard.setPrimaryClip(ClipData.newPlainText("label", newText))
                }
            }

            MyInputMethodService.ACTION_CLEAR_CLIPBOARD -> {
                clipboard.setPrimaryClip(ClipData.newPlainText("", ""))
            }
        }
    }
}