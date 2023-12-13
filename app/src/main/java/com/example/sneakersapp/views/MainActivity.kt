package com.example.sneakersapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sneakersapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startHomePageActivity()
    }

    private fun startHomePageActivity() {
        startActivity(Intent(this@MainActivity,HomePageActivity::class.java))
        finish()
    }
}