package com.example.tictactoe

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.m08finalappbrianbone.R

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
    }

    fun playGame(view: View) {
        val intent = Intent(this, GameBoardActivity::class.java)
        startActivity(intent)
    }

    fun openOptions(view: View) {
        val intent = Intent(this, OptionsActivity::class.java)
        startActivity(intent)
    }

    fun openHelp(view: View) {
        val intent = Intent(this, HelpActivity::class.java)
        startActivity(intent)
    }

    fun confirmExit(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                finishAffinity()
            }
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })
        val alert = builder.create()
        alert.show()
    }
}