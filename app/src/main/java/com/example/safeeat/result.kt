package com.example.safeeat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class result : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Show the custom dialog when the result activity is created
        showResultDialog()

        // Find the ImageView by its ID and set the drawable resource (apple image) to the ImageView
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.apple) // Replace 'apple' with the actual image name

        // Find the continue button by its ID and set an OnClickListener on it
        val continueButton = findViewById<Button>(R.id.continuebtn)
        continueButton.setOnClickListener {
            // Create an intent to start the MainActivity
            val intent = Intent(this, home::class.java)
            // Start the MainActivity
            startActivity(intent)
            // Optionally finish the current activity if you don't want it on the back stack
            finish()
        }
    }

    private fun showResultDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.activity_dialogcustom, null)
        dialogBuilder.setView(dialogView)

        val dialog = dialogBuilder.create()
        dialog.setCancelable(false)

        // Set an OnClickListener on the Continue button
        val continueButton = dialogView.findViewById<Button>(R.id.continueButton)
        continueButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
