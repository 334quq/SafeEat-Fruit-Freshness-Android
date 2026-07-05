package com.example.safeeat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class errorDialog : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reset_dialog)

        // Assume this function is called after a successful password reset
        showCustomDialog()
    }
    private fun showCustomDialog() {
        val dialogView = layoutInflater.inflate(R.layout.activity_reset_dialog, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Custom Dialog")

        val alertDialog = dialogBuilder.create()

        val resetButton = dialogView.findViewById<Button>(R.id.resetButton)
        resetButton.setOnClickListener {
            // Handle button click here
            alertDialog.dismiss()
            // Add your logic here, such as navigating back to the login activity
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish() // Optional: Close the current activity
        }

        alertDialog.show()
    }
}