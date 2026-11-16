package com.example.gguflauncher

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

object NativeBridge {
    init {
        System.loadLibrary("gguflauncher")
    }

    external fun systemInfo(): String
}

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = TextView(this).apply {
            setPadding(48, 160, 48, 48)
            textSize = 14f
            text = "llama.cpp system info:\n\n" + NativeBridge.systemInfo()
        }
        setContentView(view)
    }
}
