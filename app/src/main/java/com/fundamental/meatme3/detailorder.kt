package com.fundamental.meatme3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

@Suppress("DEPRECATION")
class detailorder : AppCompatActivity() {

    private lateinit var txtId: TextView
    private lateinit var txtProductId: TextView
    private lateinit var txtQuantity: TextView
    private lateinit var txtTotalPrice: TextView
    private lateinit var txtStatus: TextView
    private lateinit var txtCreatedAt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailorder)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        txtId = findViewById(R.id.textId)
        txtProductId = findViewById(R.id.textProductId)
        txtQuantity = findViewById(R.id.textQuantity)
        txtTotalPrice = findViewById(R.id.textTotalPrice)
        txtStatus = findViewById(R.id.textStatus)
        txtCreatedAt = findViewById(R.id.textCreatedAt)

        val selectedItem = intent.getSerializableExtra("selectedItem") as? Transaction
        if (selectedItem != null) {
            displayTransactionDetails(selectedItem)
        }
    }

    private fun displayTransactionDetails(transaction: Transaction) {
        txtId.text = "ID: ${transaction.id}"
        txtProductId.text = "Product ID: ${transaction.productId}"
        txtQuantity.text = "Quantity: ${transaction.quantity}"
        txtTotalPrice.text = "Total Price: ${transaction.totalPrice}"
        txtStatus.text = "Status: ${transaction.status}"
        txtCreatedAt.text = "Created At: ${transaction.createdAt}"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
