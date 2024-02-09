package com.example.examen02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.examen02.model.Product

class ProductsCrud : AppCompatActivity() {

    val array = MemoryDatabase.distributorsArrayList
    var productList = arrayListOf<Product>()
    var selectedItemIndex = -1
    var id: String = ""
    var name: String = ""
    var price: String = ""
    var stock: String = ""
    var isAvailable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_crud)

        selectedItemIndex = intent.getIntExtra("position", -1)
        val distributorPosition = intent.getIntExtra("distributorPosition", -1)

        productList = array[distributorPosition].productList

        if(selectedItemIndex != -1){
            val inputId = findViewById<EditText>(R.id.input_id_p)
            val inputName = findViewById<EditText>(R.id.input_name_p)
            val inputPrice = findViewById<EditText>(R.id.input_price_p)
            val inputStock = findViewById<EditText>(R.id.input_stock_p)

            inputId.setText(productList[selectedItemIndex].id.toString())
            inputName.setText(productList[selectedItemIndex].name)
            inputPrice.setText(productList[selectedItemIndex].price.toString())
            inputStock.setText(productList[selectedItemIndex].stock.toString())
        }

        val createButton = findViewById<Button>(R.id.btn_save_product)
        if (selectedItemIndex == -1) {
            createButton.setOnClickListener {
                id = findViewById<EditText>(R.id.input_id_p).text.toString()
                name = findViewById<EditText>(R.id.input_name_p).text.toString()
                price = findViewById<EditText>(R.id.input_price_p).text.toString()
                stock = findViewById<EditText>(R.id.input_stock_p).text.toString()
                if(stock.isNotEmpty()) {if(stock.toInt() != 0) {isAvailable = true}}
                /*productList.add(

                )*/
                array[distributorPosition].productList.add(
                    Product(
                        id.toInt(),
                        name,
                        price.toDouble(),
                        stock.toInt(),
                        isAvailable
                    )
                )
                response()
            }
        }

        val updateButton = findViewById<Button>(R.id.btn_save_product)
        if (selectedItemIndex != -1) {
            updateButton.setOnClickListener {
                name = findViewById<EditText>(R.id.input_name_p).text.toString()
                price = findViewById<EditText>(R.id.input_price_p).text.toString()
                stock = findViewById<EditText>(R.id.input_stock_p).text.toString()

                productList[selectedItemIndex].name = name
                productList[selectedItemIndex].price = price.toDouble()
                productList[selectedItemIndex].stock = stock.toInt()
                if(stock.toInt() != 0) {
                    productList[selectedItemIndex].isAvailable = true
                }
                response()
            }
        }
    }

    private fun response(){
        val intentReturnParameters = Intent()
        intentReturnParameters.putExtra("position", selectedItemIndex)
        setResult(
            RESULT_OK,
            intentReturnParameters
        )
        finish()
    }
}