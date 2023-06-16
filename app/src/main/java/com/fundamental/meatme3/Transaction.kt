package com.fundamental.meatme3

import java.io.Serializable

data class Transaction(
    val id: Int,
    val productId: Int,
    val quantity: Int,
    val totalPrice: String,
    val status: String,
    val createdAt: String
) : Serializable

