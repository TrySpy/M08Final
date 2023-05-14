package com.example.m08finalappbrianbone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.tictactoe.MainMenuActivity

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }
    val backButton = findViewById<Button>(R.id.back_button)
    backButton.setOnClickListener {
        val intent = Intent(this,MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}