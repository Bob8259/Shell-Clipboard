package com.example.input

import android.inputmethodservice.InputMethodService

class MyInputMethodService : InputMethodService() {
    companion object {
        const val ACTION_READ_CLIPBOARD = "com.example.input.ACTION_READ_CLIPBOARD"
        const val ACTION_SET_CLIPBOARD = "com.example.input.ACTION_SET_CLIPBOARD"
        const val ACTION_CLEAR_CLIPBOARD = "com.example.input.ACTION_CLEAR_CLIPBOARD"
        const val EXTRA_CLIPBOARD_TEXT = "com.example.input.EXTRA_CLIPBOARD_TEXT"
    }
}