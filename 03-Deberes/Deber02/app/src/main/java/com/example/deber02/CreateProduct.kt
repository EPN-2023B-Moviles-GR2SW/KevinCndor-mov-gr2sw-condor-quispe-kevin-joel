package com.example.deber02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.deber02.db.Database
import com.example.deber02.dto.ProductDto

class CreateProduct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_product)

        val goBackButton = findViewById<ImageButton>(R.id.go_back_to_product_list)
        goBackButton.setOnClickListener {
            finish()
        }

        val productName = intent.getStringExtra("productName").toString()
        val saveNewProductButton = findViewById<Button>(R.id.btn_createProduct)
        val distributorId = intent.getStringExtra("distributorId").toString()
        saveNewProductButton.setOnClickListener {
            createProduct(distributorId)
        }
    }

    private fun createProduct(distributorId: String){
        val inputNombre = findViewById<EditText>(R.id.pt_productName)
        val inputPrice = findViewById<EditText>(R.id.pt_productPrice)
        val inputStock = findViewById<EditText>(R.id.pt_productStock)

        val name = inputNombre.text.toString()
        val price = inputPrice.text.toString().toDouble()
        val stock = inputStock.text.toString().toInt()
        var isAvailable = false
        if(stock != 0){
             isAvailable = true
        }
        val newProduct = ProductDto(
            name,
            price,
            stock,
            isAvailable,
            distributorId
        )
        Database.productos!!.create(newProduct)
        finish()
    }

}