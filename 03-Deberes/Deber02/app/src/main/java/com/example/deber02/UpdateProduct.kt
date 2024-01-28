package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.deber02.db.Database
import com.example.deber02.dto.ProductDto

class UpdateProduct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)
        loadDataInEditText(intent)

        val goBackButton = findViewById<ImageButton>(R.id.ib_go_back_update_product_to_product_list)
        goBackButton.setOnClickListener { finish() }
        val saveUpdatedData = findViewById<Button>(R.id.btn_update_product)
        saveUpdatedData.setOnClickListener {
            updateProducts()
            finish()
        }
    }

    private fun updateProducts() {
        val inputName = findViewById<EditText>(R.id.pt_product_update_name)
        val inputPrice = findViewById<EditText>(R.id.pt_product_update_price)
        val inputStock = findViewById<EditText>(R.id.pt_product_update_stock)

        val name = inputName.text.toString()
        val price = inputPrice.text.toString().toDouble()
        val stock = inputStock.text.toString().toInt()
        val isAvailable = false
        if (stock != 0) true

        val distributorId = intent.getStringExtra("distributorId").toString()
        val productId = intent.getStringExtra("productId").toString()


        val updatedSucursal = ProductDto(
            name,
            price,
            stock,
            isAvailable,
            distributorId
        )

        Database.productos!!.update(productId, updatedSucursal)

        finish()
    }

    private fun loadDataInEditText(intent: Intent) {
        val name = intent.getStringExtra("name")
        val price = intent.getDoubleExtra("price",0.0)
        val stock = intent.getIntExtra("stock",0)

        val inputName = findViewById<EditText>(R.id.pt_product_update_name)
        val inputPrice = findViewById<EditText>(R.id.pt_product_update_price)
        val inputStock = findViewById<EditText>(R.id.pt_product_update_stock)

        inputName.setText(name)
        inputPrice.setText(price.toString())
        inputStock.setText(stock.toString())
    }
}