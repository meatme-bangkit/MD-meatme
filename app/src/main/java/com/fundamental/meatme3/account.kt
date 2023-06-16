package com.fundamental.meatme3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class account : AppCompatActivity() {

    lateinit var sharedPref: SharedPreferences

    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewPhone: TextView
    private lateinit var textViewAddress: TextView

    private lateinit var logoutbutton : Button

    private val client = OkHttpClient()

    private lateinit var backButton : ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_account)

        logoutbutton = findViewById(R.id.logout)
        logoutbutton.setOnClickListener {
            logout()
        }

        backButton = findViewById(R.id.backbutton)
        backButton.setOnClickListener {
            finish()
        }

        textViewName = findViewById(R.id.textViewName)
        textViewEmail = findViewById(R.id.textViewEmail)
        textViewPhone = findViewById(R.id.textViewPhone)
        textViewAddress = findViewById(R.id.textViewAddress)

        // Open shared preference
        sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", "")
        val token = sharedPref.getString("token", "")

        val emailUser = email
        val tokenUser = token
        val urlApi = "https://backendtest-upbp4qmnnq-et.a.run.app/api/profile/$emailUser"
        // Close shared preference

        fetchDataFromApi(urlApi, tokenUser.toString(), emailUser)
    }

    private fun fetchDataFromApi(urlApi: String, tokenUser: String, emailUser: String?) {

        val request = Request.Builder()
            .url(urlApi) // Ganti URL dengan URL API profil pengguna
            .header("Authorization", "Bearer $tokenUser")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                runOnUiThread {
                    try {
                        val jsonObject = JSONObject(responseData)
                        val dataArray = jsonObject.getJSONArray("data")

                        if (dataArray.length() > 0) {
                            val userData = dataArray.getJSONObject(0)
                            val name = userData.getString("name")
                            val email = userData.getString("email")
                            val phone = userData.getString("phone_number")
                            val address = userData.getString("address")

                            // Tampilkan data profil pengguna di tampilan
                            textViewName.text = "$name"
                            textViewEmail.text = "$email"
                            textViewPhone.text = "$phone"
                            textViewAddress.text = "$address"

                            // Tambahkan komponen tampilan lainnya sesuai kebutuhan

                        } else {
                            // Jika array "data" kosong
                            handleEmptyData()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                        // Tangani kesalahan parsing JSON
                        handleJsonParsingError()
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                // Tangani kesalahan koneksi atau permintaan gagal
                handleRequestFailure()
            }
        })
    }

    private fun handleEmptyData() {
        Toast.makeText(this, "data gak ada", Toast.LENGTH_SHORT).show()
    }

    private fun handleJsonParsingError() {
        Toast.makeText(this, "gagal parsing json", Toast.LENGTH_SHORT).show()
    }

    private fun handleRequestFailure() {
        Toast.makeText(this, "gagal request", Toast.LENGTH_SHORT).show()
    }


    private fun logout() {
        // Hapus bearer token dari tempat penyimpanan lokal
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove("token")
        editor.apply()

        // Periksa apakah bearer token sudah dihapus
        val isTokenDeleted = !sharedPref.contains("token")

        if (isTokenDeleted) {
            // Bearer token berhasil dihapus, lanjutkan ke intent berikutnya
            val intent = Intent(this, login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            Toast.makeText(this, "Berhasil logout", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            // Menutup activity saat ini agar tidak dapat dikembalikan dengan tombol back

        } else {
            // Bearer token gagal dihapus, lakukan penanganan yang sesuai
            Toast.makeText(this, "Failed to logout. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }


}
