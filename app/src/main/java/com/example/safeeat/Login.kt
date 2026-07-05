package com.example.safeeat

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {

    private lateinit var loginEmail: EditText
    private lateinit var loginPass: EditText
    private lateinit var button4: Button
    private lateinit var passwordVisibilityToggle: ImageView
    private var isPasswordVisible = false
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loginEmail = findViewById(R.id.loginEmail)
        loginPass = findViewById(R.id.loginPass)
        button4 = findViewById(R.id.button4)
        passwordVisibilityToggle = findViewById(R.id.passwordVisibilityToggle)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        loginPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        button4.setOnClickListener {
            val email = loginEmail.text.toString()
            val password = loginPass.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                showMessage("Email and password are required.")
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.let {
                                db.collection("users").document(user.uid).get()
                                    .addOnSuccessListener { document ->
                                        if (document != null) {
                                            val username = document.getString("username")
                                            showMessage("Welcome, $username")
                                            val intent = Intent(this, home::class.java)
                                            startActivity(intent)
                                            finish()
                                        } else {
                                            showMessage("No user data found")
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        showMessage("Failed to retrieve user data: ${e.message}")
                                    }
                            }
                        } else {
                            showMessage("Invalid email or password")
                        }
                    }
            }
        }

        passwordVisibilityToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            val drawable =
                if (isPasswordVisible) R.drawable.hide_pass else R.drawable.show_pass
            passwordVisibilityToggle.setImageResource(drawable)
            loginPass.inputType =
                if (isPasswordVisible) InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            loginPass.setSelection(loginPass.length())
        }

        val textSign = findViewById<TextView>(R.id.textSign)
        textSign.setOnClickListener {
            val intent = Intent(this, singin::class.java)
            startActivity(intent)
        }

        val textForgot = findViewById<TextView>(R.id.textForgot)
        textForgot.setOnClickListener {
            val intent = Intent(this, forgotPass::class.java)
            startActivity(intent)
        }
    }

    private fun showMessage(message: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val alert = dialogBuilder.create()
        alert.show()
    }
}
