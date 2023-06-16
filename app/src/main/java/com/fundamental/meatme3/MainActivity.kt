package com.fundamental.meatme3

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var buttoncamera: Button
    private lateinit var buttonChoose: Button
    private lateinit var buttonUpload: Button

    private val PICK_IMAGE_REQUEST = 1
    private val REQUEST_IMAGE_CAPTURE = 2

    private val API_URL = "https://mlapi-upbp4qmnnq-et.a.run.app/predict"


    private lateinit var backbutton : ImageButton







    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        backbutton = findViewById(R.id.backbutton)
        backbutton.setOnClickListener {
            finish()
        }


        imageView = findViewById(R.id.imageView)
        buttoncamera = findViewById(R.id.buttoncamera)
        buttonChoose = findViewById(R.id.buttonChoose)
        buttonUpload = findViewById(R.id.buttonUpload)

        buttonChoose.setOnClickListener {
            openGallery()
        }

        buttonUpload.setOnClickListener {
            uploadImage()
        }

        buttoncamera.setOnClickListener {
            takePicture()
        }
    }

    // Fungsi untuk meminta izin akses kamera
    private fun requestCameraPermission() {
        requestPermission.launch(android.Manifest.permission.CAMERA)
    }

    // Fungsi untuk mengambil gambar menggunakan kamera
    private fun takePicture() {
        requestCameraPermission()
    }

    // Launch camera and take picture
    val takePicturePreview =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                createImageFile(bitmap)
            }
        }

    // Request camera permission
    val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                takePicturePreview.launch(null)
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImage = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                imageView.setImageBitmap(bitmap)
                createImageFile(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        val bitmap = (imageView.drawable as? BitmapDrawable)?.bitmap

        if (bitmap != null) {
            val imageFile = createImageFile(bitmap)
            if (imageFile != null) {
                val requestBody = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)

                // Menampilkan dialog pop-up loading
                val progressDialog = ProgressDialog(this,R.style.CustomAlertDialog )
                progressDialog.setMessage("LOADING..")
                progressDialog.setCancelable(false)
                progressDialog.show()

                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        val response = ApiClient.apiService.uploadImage(imagePart)
                        if (response.isSuccessful) {
                            val apiResponse = response.body()
                            val classValue = apiResponse?.category ?: "No Class Found"
                            val confidence = apiResponse?.confidence ?: 0.0
                            val message = "Tingkat kesegaran: $classValue\n" +
                                    "Nilai prediksi: $confidence\n\n" +
                                    if (classValue == "Fresh") "Fresh : Indicates that the meat is fresh by having a bright color and is fit for consumption" else if
                                        (classValue == "Half Fresh") " Half Fresh : In general, half fresh meat is not common in everyday life. In this application, half fresh meat signifies where the meat is not fresh, but not spoiled either or not reaching the level of spoilage like spoiled meat" else if
                                             (classValue == "Spoiled") "Spoiled : Indicates that the meat is spoiled or rotten, making it unfit for consumption due to spoilage" else "Not Valid Image : The image you entered is not a picture of meat"
                            showDialog("Hasil deteksi", message)
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Error: ${response.message()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    } finally {
                        // Menyembunyikan dialog pop-up
                        progressDialog.dismiss()
                    }
                }
            } else {
                Toast.makeText(this, "Failed to create image file.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No image selected.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createImageFile(bitmap: Bitmap): File? {
        var file: File? = null
        try {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            if (dir != null) {
                if (!dir.exists()) {
                    dir.mkdirs()
                }
            }
            file = File(dir, "image.jpg")
            val fos = FileOutputStream(file)
            fos.write(bytes.toByteArray())
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }

}
