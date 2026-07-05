package com.example.safeeat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.text.InputType
import android.app.AlertDialog
import android.content.Intent
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class singin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_singin)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameEditText = findViewById<EditText>(R.id.username)
        val emailEditText = findViewById<EditText>(R.id.signEmail)
        val passwordEditText = findViewById<EditText>(R.id.signPass)
        val confirmPasswordEditText = findViewById<EditText>(R.id.signPassCon)
        val signupButton = findViewById<Button>(R.id.button5)

        passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        confirmPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        val passwordVisibilityToggle = findViewById<ImageView>(R.id.signPasswordVisibilityToggle)
        val confirmPasswordVisibilityToggle = findViewById<ImageView>(R.id.conPasswordVisibilityToggle)

        passwordVisibilityToggle.setOnClickListener {
            togglePasswordVisibility(passwordEditText, passwordVisibilityToggle)
        }

        confirmPasswordVisibilityToggle.setOnClickListener {
            togglePasswordVisibility(confirmPasswordEditText, confirmPasswordVisibilityToggle)
        }

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        signupButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showAlertDialog("Please fill in all fields")
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                showAlertDialog("Passwords do not match")
                return@setOnClickListener
            }

            signUpUser(username, email, password, auth, db)
        }

        val textLogin = findViewById<TextView>(R.id.textLogin)
        textLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    private fun showAlertDialog(message: String, goToLogin: Boolean = false) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                if (goToLogin) {
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish() // Optionally finish the current activity
                }
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView) {
        if (editText.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            imageView.setImageResource(R.drawable.hide_pass)
        } else {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            imageView.setImageResource(R.drawable.show_pass)
        }
        editText.setSelection(editText.length())
    }

    private fun signUpUser(username: String, email: String, password: String, auth: FirebaseAuth, db: FirebaseFirestore) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val userData = hashMapOf(
                            "email" to email,
                            "username" to username
                        )
                        db.collection("users").document(user.uid)
                            .set(userData)
                            .addOnSuccessListener {
                                showAlertDialog("Signup successful", true)
                            }
                            .addOnFailureListener { e ->
                                showAlertDialog("Failed to save user data: ${e.message}")
                            }
                    }
                } else {
                    showAlertDialog(task.exception?.message ?: "Signup failed")
                }
            }
    }
}
