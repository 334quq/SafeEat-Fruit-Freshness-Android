package com.example.safeeat

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

class classification : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classification)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val textView = findViewById<TextView>(R.id.classtext)
        val continueButton = findViewById<Button>(R.id.continuebtn)

        val imageUriString = intent.getStringExtra("imageUri")
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            imageView.setImageURI(imageUri)

            // Convert imageUri to File
            val imageFile = File(imageUri.path ?: return)
            uploadImage(imageFile)
        }

        //textView.text = "Classification Result: "

        continueButton.setOnClickListener {
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }
    }

    private fun uploadImage(imageFile: File) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.MODEL_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val mediaType = "image/jpeg".toMediaTypeOrNull()
        val requestFile = RequestBody.create(mediaType, imageFile)
        val body = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)

        val call = service.uploadImage(body)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val jsonResponse = response.body()?.string() ?: return
                        Log.d("Response", jsonResponse)

                        // Parse the JSON response
                        val jsonObject = JSONObject(jsonResponse)
                        val base64Image = jsonObject.getString("image")

                        // Decode base64 image
                        val decodedString = Base64.decode(base64Image, Base64.DEFAULT)
                        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                        // Update the ImageView with the image
                        runOnUiThread {
                            val imageView: ImageView = findViewById(R.id.imageView)
                            imageView.setImageBitmap(decodedByte)

                            // Handle results if needed
                            val resultsArray = jsonObject.getJSONArray("results")
                            val resultsBuilder = StringBuilder()
                            for (i in 0 until resultsArray.length()) {
                                val result = resultsArray.getJSONObject(i)
                                resultsBuilder.append("Name: ${result.getString("name")}, Confidence: ${result.getDouble("confidence")}\n")
                            }
                            val textView: TextView = findViewById(R.id.classtext)
                            textView.text = "Classification Result: ${resultsBuilder.toString()}"
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    // Handle error response
                    runOnUiThread {
                        val textView: TextView = findViewById(R.id.classtext)
                        //textView.text = "Error: ${response.errorBody()?.string()}"
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle failure
                runOnUiThread {
                    val textView: TextView = findViewById(R.id.classtext)
                    //textView.text = "Failure: ${t.message}"
                }
            }
        })
    }

    interface ApiService {
        @Multipart
        @POST("/predict")
        fun uploadImage(@Part file: MultipartBody.Part): Call<ResponseBody>
    }
}
