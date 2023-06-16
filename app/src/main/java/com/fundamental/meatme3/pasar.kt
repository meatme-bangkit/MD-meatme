package com.fundamental.meatme3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class pasar : AppCompatActivity() {

    private lateinit var editTextSearch: EditText
    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var sharedPref: SharedPreferences
    private lateinit var textViewName: TextView
    private lateinit var cart1 :ImageView



    private lateinit var produk1 : TextView
    private lateinit var produk2 : TextView
    private lateinit var produk3 : TextView
    private lateinit var produk4 : TextView
    private lateinit var produk5 : TextView
    private lateinit var produk6 : TextView
    private lateinit var produk7 : TextView
    private lateinit var produk8 : TextView


    private val client = OkHttpClient()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_pasar)

        cart1 = findViewById(R.id.cart)
        cart1.setOnClickListener {
            startActivity(Intent(this, cart::class.java))
        }

        // Open shared preference
        sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", "")
        val token = sharedPref.getString("token", "")

        val emailUser = email
        val tokenUser = token
        val urlApi = "https://backendtest-upbp4qmnnq-et.a.run.app/api/profile/$emailUser"
        // Close shared preference

        fetchDatauser(urlApi, tokenUser.toString(), emailUser)


        //ngambil id dari produk

        // produk 1
        var produkimage1 = findViewById<ImageView>(R.id.product_image_1)
        produk1 = findViewById(R.id.product_title_1)
        val produkid1 = produk1.text.toString()
        produkimage1.setOnClickListener {
            var intent = Intent(this, detailproduct::class.java)
            intent.putExtra("namadaging", produkid1)
            startActivity(intent)
        }


        // produk 2
        var produkimage2 = findViewById<ImageView>(R.id.product_image_2)
        produk2 = findViewById(R.id.product_title_2)
        val produkid2 = produk2.text.toString()
        produkimage2.setOnClickListener {
            var intent = Intent(this, detailproduct::class.java)
            intent.putExtra("namadaging",produkid2)
            startActivity(intent)
        }



        // produk 3
        var produkimage3 = findViewById<ImageView>(R.id.product_image_3)
        produk3 = findViewById(R.id.product_title_3)
        val produkid3 = produk3.text.toString()
        produkimage3.setOnClickListener {
            var intent = Intent(this,detailproduct::class.java)
            intent.putExtra("namadaging", produkid3)
            startActivity(intent)
        }


        // produk 4
        var produkimage4 = findViewById<ImageView>(R.id.product_image_4)
        produk4 = findViewById(R.id.product_title_4)
        val produkid4 = produk4.text.toString()
        produkimage4.setOnClickListener {
            var intent = Intent(this, detailproduct::class.java)
            intent.putExtra("namadaging", produkid4)
            startActivity(intent)
        }





        //produk 5
        var produkimage5 = findViewById<ImageView>(R.id.product_image_5)
        produk5 = findViewById(R.id.product_title_5)
        val produkid5 = produk5.text.toString()
        produkimage5.setOnClickListener {
            var intent = Intent(this, detailproduct::class.java)
            intent.putExtra("namadaging", produkid5)
            startActivity(intent)
        }






        // produk 6
        var produkimage6 = findViewById<ImageView>(R.id.product_image_6)
        produk6 = findViewById(R.id.product_title_6)
        val produkid6 = produk6.text.toString()
        produkimage6.setOnClickListener {
            var intent = Intent(this, detailproduct::class.java)
            intent.putExtra("namadaging", produkid6)
            startActivity(intent)
        }






        //produk 7
        var produkimage7 = findViewById<ImageView>(R.id.product_image_7)
        produk7 = findViewById(R.id.product_title_7)
        val produkid7 = produk7.text.toString()
        produkimage7.setOnClickListener {
            var intent = Intent(this, detailproduct::class.java)
            intent.putExtra("namadaging", produkid7)
            startActivity(intent)
        }



        // produk 8
        var produkimage8 = findViewById<ImageView>(R.id.product_image_8)
        produk8 = findViewById(R.id.product_title_8)
        val produkid8 = produk1.text.toString()
        produkimage8.setOnClickListener {
            var intent = Intent(this, detailproduct::class.java)
            intent.putExtra("namadaging", produkid8)
            startActivity(intent)
        }



















        textViewName = findViewById(R.id.welcometext)

        //open bottom navigation
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.pasar -> {
                    true
                }
                R.id.deteksi -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.akun -> {
                    startActivity(Intent(this, account::class.java))
                    true
                }
                else -> false
            }
        }
        // close bottom navigation




        // open search
        editTextSearch = findViewById(R.id.editTextSearch)
        editTextSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchQuery = editTextSearch.text.toString()
                performSearch(searchQuery)
                return@setOnEditorActionListener true
            }
            false
        }
        // close search





        val imageViewProfile1 = findViewById<ImageView>(R.id.product_image_1)
        val imageUrl1 = "https://storage.googleapis.com/cobatest/1685594211657_chuck.jpeg"

        Picasso.get()
            .load(imageUrl1)
            .into(imageViewProfile1)

        val imageViewProfile2 = findViewById<ImageView>(R.id.product_image_2)
        val imageUrl2 = "https://storage.googleapis.com/cobatest/1685594381766_brisket.jpg"

        Picasso.get()
            .load(imageUrl2)
            .into(imageViewProfile2)

        val imageViewProfile3 = findViewById<ImageView>(R.id.product_image_3)
        val imageUrl3 = "https://storage.googleapis.com/cobatest/1685594514047_ribs.jpeg"

        Picasso.get()
            .load(imageUrl3)
            .into(imageViewProfile3)

        val imageViewProfile4 = findViewById<ImageView>(R.id.product_image_4)
        val imageUrl4 = "https://storage.googleapis.com/cobatest/1685594615276_plate.jpeg"

        Picasso.get()
            .load(imageUrl4)
            .into(imageViewProfile4)

        val imageViewProfile5 = findViewById<ImageView>(R.id.product_image_5)
        val imageUrl5 = "https://storage.googleapis.com/cobatest/1685594736055_sirloin.jpeg"

        Picasso.get()
            .load(imageUrl5)
            .into(imageViewProfile5)

        val imageViewProfile6 = findViewById<ImageView>(R.id.product_image_6)
        val imageUrl6 = "https://storage.googleapis.com/cobatest/1685594888757_tenderloin.jpg"

        Picasso.get()
            .load(imageUrl6)
            .into(imageViewProfile6)

        val imageViewProfile7 = findViewById<ImageView>(R.id.product_image_7)
        val imageUrl7 = "https://storage.googleapis.com/cobatest/1685595063948_flank.jpg"

        Picasso.get()
            .load(imageUrl7)
            .into(imageViewProfile7)

        val imageViewProfile8 = findViewById<ImageView>(R.id.product_image_8)
        val imageUrl8 = "https://storage.googleapis.com/cobatest/1685595234793_round.jpeg"

        Picasso.get()
            .load(imageUrl8)
            .into(imageViewProfile8)







    }






    // fungsi untuk cek pencarian
    private fun performSearch(query: String) {
        if (query.isNotBlank()) {
            fetchDataFromApi(query)
        } else {
            Toast.makeText(applicationContext, "Kolom pencarian tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
    }




    // fungsi untuk mengambil data di API sesuai dengan nilai dari query
    private fun fetchDataFromApi(query: String) {
        val url = "https://backendtest-upbp4qmnnq-et.a.run.app/api/products/search/$query"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                runOnUiThread {
                    try {
                        val jsonArray = JSONArray(responseData)
                        if (jsonArray.length() > 0) {
                            val userData = jsonArray.getJSONObject(0)
                            val id = userData.getString("id")
                            val address = userData.getString("address")
                            val meatname = userData.getString("meatname")
                            val detail = userData.getString("details")
                            val stock = userData.getString("stock")
                            val price = userData.getString("price")
                            val seller = userData.getString("seller")
                            val image = userData.getString("image")


                            val intent = Intent(this@pasar, resultsearch::class.java)
                            intent.putExtra("id", id)
                            intent.putExtra("address", address)
                            intent.putExtra("meatname", meatname)
                            intent.putExtra("detail", detail)
                            intent.putExtra("stock", stock)
                            intent.putExtra("price", price)
                            intent.putExtra("seller", seller)
                            intent.putExtra("image", image)
                            startActivity(intent)

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
        Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
    }

    private fun handleJsonParsingError() {
        Toast.makeText(this, "Gagal parsing JSON", Toast.LENGTH_SHORT).show()
    }

    private fun handleRequestFailure() {
        Toast.makeText(this, "Gagal melakukan permintaan", Toast.LENGTH_SHORT).show()
    }










    private fun fetchDatauser(urlApi: String, tokenUser: String, emailUser: String?) {

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


                            // Tampilkan data profil pengguna di tampilan
                            textViewName.text = "Selamat Datang, $name !"


                            // Tambahkan komponen tampilan lainnya sesuai kebutuhan

                        } else {
                            // Jika array "data" kosong
                            handleEmptyData1()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                        // Tangani kesalahan parsing JSON
                        handleJsonParsingError1()
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                // Tangani kesalahan koneksi atau permintaan gagal
                handleRequestFailure1()
            }
        })
    }

    private fun handleEmptyData1() {
        Toast.makeText(this, "data gak ada", Toast.LENGTH_SHORT).show()
    }

    private fun handleJsonParsingError1() {
        Toast.makeText(this, "gagal parsing json", Toast.LENGTH_SHORT).show()
    }

    private fun handleRequestFailure1() {
        Toast.makeText(this, "gagal request", Toast.LENGTH_SHORT).show()
    }



}
