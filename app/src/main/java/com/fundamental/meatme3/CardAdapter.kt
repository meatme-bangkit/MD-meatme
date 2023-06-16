package com.fundamental.meatme3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class CardAdapter(context: Context, transactions: List<Transaction>) :
    ArrayAdapter<Transaction>(context, 0, transactions) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false)
            viewHolder = ViewHolder()
            viewHolder.textId = view.findViewById(R.id.textId)
            viewHolder.textProductId = view.findViewById(R.id.textProductId)
            viewHolder.textQuantity = view.findViewById(R.id.textQuantity)
            viewHolder.textTotalPrice = view.findViewById(R.id.textTotalPrice)
            viewHolder.textStatus = view.findViewById(R.id.textStatus)
            viewHolder.textCreatedAt = view.findViewById(R.id.textCreatedAt)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val transaction = getItem(position)

        if (transaction != null) {
            viewHolder.textId?.text = "ID: ${transaction.id}"
            viewHolder.textProductId?.text = "Product ID: ${transaction.productId}"
            viewHolder.textQuantity?.text = "Quantity: ${transaction.quantity}"
            viewHolder.textTotalPrice?.text = "Total Price: ${transaction.totalPrice}"
            viewHolder.textStatus?.text = "Status: ${transaction.status}"
            viewHolder.textCreatedAt?.text = "Created At: ${transaction.createdAt}"
        }

        return view!!
    }

    private class ViewHolder {
        var textId: TextView? = null
        var textProductId: TextView? = null
        var textQuantity: TextView? = null
        var textTotalPrice: TextView? = null
        var textStatus: TextView? = null
        var textCreatedAt: TextView? = null
    }
}
