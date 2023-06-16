package com.fundamental.meatme3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class detailproductsearch : AppCompatActivity() {


    lateinit var sharedPref: SharedPreferences

    private lateinit var checkoutbutton: Button

    private val client = OkHttpClient()


    private var jumlahDaging: Int = 0
    private lateinit var beli1: EditText


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_detailproductsearch)



        val plusButton = findViewById<Button>(R.id.tambahstok)
        val minusButton = findViewById<Button>(R.id.kurangstok)

        beli1 = findViewById(R.id.beli)


        plusButton.setOnClickListener {
            incrementJumlahDaging()
        }

        minusButton.setOnClickListener {
            decrementJumlahDaging()
        }

        val id = intent.getStringExtra("id")

        val detail = intent.getStringExtra("detail")
        val meatname1 = intent.getStringExtra("meatname")
        val stock = intent.getStringExtra("stock")
        val price = intent.getStringExtra("price")
        val seller = intent.getStringExtra("seller")
        val image = intent.getStringExtra("image")



        //val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val meatname = findViewById<TextView>(R.id.namaproduk)
        val deskripsi = findViewById<TextView>(R.id.deskripsiproduk)
        val harga = findViewById<TextView>(R.id.hargaproduk)
        val namapenjual = findViewById<TextView>(R.id.namapenjual)
        val stok = findViewById<TextView>(R.id.stokproduk)



        val imageset = findViewById<ImageView>(R.id.gambar)

        Picasso.get()
            .load(image)
            .into(imageset)

        //nameTextView.text = address
        meatname.text = meatname1
        deskripsi.text = detail
        harga.text = price
        namapenjual.text = seller
        stok.text = stock

        // Open shared preference
        sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "")

        val tokenUser = token
        // Close shared preference





        checkoutbutton = findViewById(R.id.buttoncheckout)

        checkoutbutton.setOnClickListener {

            var jumlahbeli = beli1.text.toString()


            val builder = AlertDialog.Builder(this@detailproductsearch)
            builder.setTitle("Konfirmasi")
            builder.setMessage("Apakah Anda yakin ingin memesan ?")
            builder.setPositiveButton("Ya") { dialog, which ->
                val productID = id.toString()
                val quantity = jumlahbeli

                // Buat objek JSON
                val orderan = JSONObject()
                orderan.put("productId", productID)
                orderan.put("quantity", quantity)

                // Buat request ke API untuk login
                val request = Request.Builder()
                    .url("https://backendtest-upbp4qmnnq-et.a.run.app/api/order")
                    .header("Authorization", "Bearer $tokenUser")
                    .post(orderan.toString().toRequestBody("application/json".toMediaTypeOrNull()))
                    .build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body?.string()
                        runOnUiThread {
                            try {
                                val jsonResponse = JSONObject(responseData)
                                val success = jsonResponse.getString("message")


                                if (success == "Order created successfully") {
                                    Toast.makeText(this@detailproductsearch, "berhasil memesan", Toast.LENGTH_SHORT).show()
                                    // Jika login berhasil, tambahkan kode untuk pindah ke halaman utama
                                    val intent = Intent(this@detailproductsearch, pasar::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    val errorMessage = jsonResponse.getString("message")
                                    Toast.makeText(this@detailproductsearch, errorMessage, Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                                Toast.makeText(this@detailproductsearch, "Error stacktrace : " + e.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                        runOnUiThread {
                            Toast.makeText(this@detailproductsearch, "Error: " + e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })





            }
            builder.setNegativeButton("Tidak") { dialog, which ->
                // Aksi yang dijalankan jika tombol "Tidak" diklik
                // Tambahkan kode yang ingin dijalankan di sini
            }
            builder.setCancelable(false)
            val dialog = builder.create()
            dialog.show()






        }





    }




    private fun incrementJumlahDaging() {
        jumlahDaging++
        updateJumlahDagingText()
    }
    private fun decrementJumlahDaging() {
        if (jumlahDaging > 0) {
            jumlahDaging--
            updateJumlahDagingText()
        }
    }
    private fun updateJumlahDagingText() {
        beli1.setText(jumlahDaging.toString())
    }





}