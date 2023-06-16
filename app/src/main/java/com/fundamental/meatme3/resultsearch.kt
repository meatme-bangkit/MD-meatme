package com.fundamental.meatme3

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.squareup.picasso.Picasso

class resultsearch : AppCompatActivity() {

    private lateinit var backButton : ImageButton
    private lateinit var image2 : ImageView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_resultsearch)

        backButton = findViewById(R.id.backbutton)
        backButton.setOnClickListener {
            finish()
        }


        val id = intent.getStringExtra("id")
        val detail = intent.getStringExtra("detail")
        val meatname = intent.getStringExtra("meatname")
        val stock = intent.getStringExtra("stock")
        val price = intent.getStringExtra("price")
        val seller = intent.getStringExtra("seller")
        val image = intent.getStringExtra("image")

        image2 = findViewById(R.id.image1)
        image2.setOnClickListener {
            val intent = Intent(this@resultsearch, detailproductsearch::class.java)
            intent.putExtra("id", id)
            intent.putExtra("meatname", meatname)
            intent.putExtra("detail", detail)
            intent.putExtra("stock", stock)
            intent.putExtra("price", price)
            intent.putExtra("seller", seller)
            intent.putExtra("image", image)
            startActivity(intent)

        }




        // Menampilkan data di halaman tujuan
        //val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val emailTextView = findViewById<TextView>(R.id.emailTextView)

        val imageset = findViewById<ImageView>(R.id.image1)

        Picasso.get()
            .load(image)
            .into(imageset)

        //nameTextView.text = address
        emailTextView.text = meatname

    }
}
