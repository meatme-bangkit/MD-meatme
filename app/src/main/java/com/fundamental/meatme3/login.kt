package com.fundamental.meatme3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.w3c.dom.Text
import java.io.IOException

@Suppress("DEPRECATION")
class login : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private val client = OkHttpClient()
    private lateinit var register1 : Button

    private lateinit var sharedPref: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_login)

        // Hilangkan status bar pada activity saat ini
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        register1 = findViewById(R.id.registerbutton)
        register1.setOnClickListener {
            startActivity(Intent(this, register::class.java))
        }


        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.buttonlogin)



        loginButton.setOnClickListener {
            val emailtext = email.text.toString()
            val passwordtext = password.text.toString()

            // Buat objek JSON dengan username dan password
            val loginData = JSONObject()
            loginData.put("email", emailtext)
            loginData.put("password", passwordtext)

            // Buat request ke API untuk login
            val request = Request.Builder()
                .url("https://backendtest-upbp4qmnnq-et.a.run.app/api/signin")
                .post(loginData.toString().toRequestBody("application/json".toMediaTypeOrNull()))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string()
                    runOnUiThread {
                        try {
                            val jsonResponse = JSONObject(responseData)
                            val success = jsonResponse.getString("message")
                            val emailjson = jsonResponse.getString("email")
                            val tokenjson = jsonResponse.getString("token")

                            if (success == "Login Success!") {
                                val editor = sharedPref.edit()
                                editor.putString("email", emailjson)
                                editor.putString("token", tokenjson)
                                editor.apply()
                                // Jika login berhasil, tambahkan kode untuk pindah ke halaman utama
                                val intent = Intent(this@login, pasar::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                val errorMessage = jsonResponse.getString("message")
                                Toast.makeText(this@login, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            Toast.makeText(this@login, "Error json: " + e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(this@login, "Error failure: " + e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }



    }
}
