package com.example.safeeat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.app.AlertDialog
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class feedimage : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_feedimage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        val imageUriString = intent.getStringExtra("imageUri")
        imageUri = Uri.parse(imageUriString)
        val selectedImage = findViewById<ImageView>(R.id.feedimageView)
        selectedImage.setImageURI(imageUri)

        val feedbackText = findViewById<EditText>(R.id.feedtext)
        val sendButton = findViewById<Button>(R.id.sendFeedButton)

        sendButton.setOnClickListener {
            val feedback = feedbackText.text.toString()
            if (feedback.isEmpty()) {
                showAlertDialog("Please enter feedback")
            } else {
                uploadImageToFirebase(feedback)
            }
        }
    }

    private fun uploadImageToFirebase(feedback: String) {
        val imageRef = storageReference.child("images/${imageUri.lastPathSegment}")
        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    saveFeedbackToFirebase(feedback, uri.toString())
                }
            }
            .addOnFailureListener { e ->
                showAlertDialog("Failed to upload image: ${e.message}")
            }
    }

    private fun saveFeedbackToFirebase(feedback: String, imageUrl: String) {
        val feedbackData = hashMapOf(
            "feedback" to feedback,
            "imageUrl" to imageUrl
        )

        db.collection("feedbacks")
            .add(feedbackData)
            .addOnSuccessListener { documentReference ->
                val intent = Intent(this, feedsend::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                showAlertDialog("Failed to send feedback: ${e.message}")
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
}
