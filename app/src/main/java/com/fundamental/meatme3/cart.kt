package com.fundamental.meatme3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException


class cart : AppCompatActivity() {

    private lateinit var listView: ListView

    lateinit var sharedPref: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_cart)

        // Open shared preference
        sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "")
        val tokenUser = token
        // Close shared preference

        listView = findViewById(R.id.listView)

        val url = "https://backendtest-upbp4qmnnq-et.a.run.app/api/orders" // Ganti dengan URL API yang sesuai

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $tokenUser")
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle error ketika terjadi kegagalan permintaan
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body?.string()

                runOnUiThread {
                    if (jsonString != null) {
                        val transactions = parseTransactionsFromJson(jsonString)
                        val adapter = CardAdapter(this@cart, transactions)
                        listView.adapter = adapter

                        listView.onItemClickListener =
                            AdapterView.OnItemClickListener { _, _, position, _ ->
                                val selectedItem = transactions[position]
                                val intent = Intent(this@cart, detailorder::class.java)
                                intent.putExtra("selectedItem", selectedItem)
                                startActivity(intent)
                            }
                    } else {
                        // Handle error jika respons JSON kosong atau tidak valid
                    }
                }
            }
        })
    }

    private fun parseTransactionsFromJson(jsonString: String): List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        try {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val productId = jsonObject.getInt("product_id")
                val quantity = jsonObject.getInt("quantity")
                val totalPrice = jsonObject.getString("total_price")
                val status = jsonObject.getString("status")
                val createdAt = jsonObject.getString("created_at")

                val transaction = Transaction(id, productId, quantity, totalPrice, status, createdAt)
                transactions.add(transaction)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return transactions
    }
}
