package com.oskr19.testapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("oskr19://shop/search")
            startActivity(this)
        }
    }
}
