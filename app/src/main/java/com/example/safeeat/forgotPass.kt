package com.example.safeeat

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class forgotPass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_pass)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val forgotPassEditText = findViewById<EditText>(R.id.forgotPass)
        val forgotPassVisibilityToggle = findViewById<ImageView>(R.id.forgotPasswordVisibilityToggle)
        val forgotConPassEditText = findViewById<EditText>(R.id.forgotConPass)
        val forgotConPassVisibilityToggle = findViewById<ImageView>(R.id.forgotConPasswordVisibilityToggle)

        // Set input type for password fields
        forgotPassEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        forgotConPassEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        // Password visibility toggle for new password
        forgotPassVisibilityToggle.setOnClickListener {
            togglePasswordVisibility(forgotPassEditText, forgotPassVisibilityToggle)
        }

        // Password visibility toggle for confirm password
        forgotConPassVisibilityToggle.setOnClickListener {
            togglePasswordVisibility(forgotConPassEditText, forgotConPassVisibilityToggle)
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            // Add your reset password logic here
            val newPassword = forgotPassEditText.text.toString()
            val confirmPassword = forgotConPassEditText.text.toString()

            if (newPassword != confirmPassword) {
                showAlertDialog("Passwords do not match")
            } else {
                showCustomDialog()
            }
        }
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showCustomDialog() {
        val dialogView = layoutInflater.inflate(R.layout.activity_reset_dialog, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()

        val btnOk = dialogView.findViewById<Button>(R.id.resetButton)
        btnOk.setOnClickListener {
            // Handle button click here
            alertDialog.dismiss()

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        alertDialog.show()
    }

    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView) {
        val isPasswordVisible = editText.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        if (isPasswordVisible) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            imageView.setImageResource(R.drawable.hide_pass)
        } else {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            imageView.setImageResource(R.drawable.show_pass)
        }
        editText.setSelection(editText.text.length)
    }
}

