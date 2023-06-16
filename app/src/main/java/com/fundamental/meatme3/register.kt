package com.fundamental.meatme3

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

@Suppress("DEPRECATION")
class register : AppCompatActivity() {
    private lateinit var nama: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var phone: EditText
    private lateinit var addressuser: EditText

    private lateinit var registerbutton: Button
    private val client = OkHttpClient()
    private lateinit var login1: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_register)

        // Hilangkan status bar pada activity saat ini
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        login1 = findViewById(R.id.loginbutton)
        login1.setOnClickListener {
            startActivity(Intent(this, login::class.java))
        }

        nama = findViewById(R.id.nama)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        phone = findViewById(R.id.phonenumber)
        addressuser = findViewById(R.id.address)

        registerbutton = findViewById(R.id.buttonregister)

        registerbutton.setOnClickListener {
            val namatext = nama.text.toString()
            val emailtext = email.text.toString()
            val passwordtext = password.text.toString()
            val phonenumbertext = phone.text.toString()
            val addresstext = addressuser.text.toString()


            // Validasi minimal 10 karakter
            if (passwordtext.length < 10) {
                Toast.makeText(this@register, "password minimal 10 karakter", Toast.LENGTH_SHORT).show()
            }

            // Buat objek JSON
            val registerData = JSONObject()
            registerData.put("name", namatext)
            registerData.put("email", emailtext)
            registerData.put("password", passwordtext)
            registerData.put("phone_number", phonenumbertext)
            registerData.put("address", addresstext)

            // Buat request ke API untuk login
            val request = Request.Builder()
                .url("https://backendtest-upbp4qmnnq-et.a.run.app/api/signup")
                .post(registerData.toString().toRequestBody("application/json".toMediaTypeOrNull()))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string()
                    runOnUiThread {
                        try {
                            val jsonResponse = JSONObject(responseData)
                            val success = jsonResponse.getString("message")

                            if (success == "Account Successfully Registered!") {
                                // Jika registrasi berhasil, tambahkan kode untuk pindah ke halaman utama
                                val intent = Intent(this@register, login::class.java)
                                Toast.makeText(this@register, "Berhasil Registrasi", Toast.LENGTH_SHORT).show()
                                startActivity(intent)
                                finish()
                            } else {
                                val errorMessage = jsonResponse.getString("message")
                                Toast.makeText(this@register, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            Toast.makeText(this@register, "Error json trace: " + e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(this@register, "Error print trace: " + e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}
