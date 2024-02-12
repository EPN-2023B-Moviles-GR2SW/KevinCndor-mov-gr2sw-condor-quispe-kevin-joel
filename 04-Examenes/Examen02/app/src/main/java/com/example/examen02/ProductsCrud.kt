package com.example.examen02

import android.content.ContentValues
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.examen02.model.Distributor
import com.example.examen02.model.Product
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ProductsCrud : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val distributorsCollection = db.collection("distributors")
    private var nameF = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_crud)

        // Obtener el índice y la información del producto del intent

        val selectedIndexItem = intent.getIntExtra("position", -1)
        val distributorId = intent.getStringExtra("distributorId")
        val productId = intent.getIntExtra("productId", -1)
        val productName = intent.getStringExtra("productName")
        val productPrice = intent.getDoubleExtra("productPrice", 0.0)
        val productStock = intent.getIntExtra("productStock", 0)
        nameF = intent.getStringExtra("nameF").toString()


        findViewById<EditText>(R.id.input_id_p).setText(productId.toString())
        findViewById<EditText>(R.id.input_name_p).setText(productName)
        findViewById<EditText>(R.id.input_price_p).setText(productPrice.toString())
        findViewById<EditText>(R.id.input_stock_p).setText(productStock.toString())


        val saveButton = findViewById<Button>(R.id.btn_save_product)
        saveButton.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id_p).text.toString().toInt()
            val name = findViewById<EditText>(R.id.input_name_p).text.toString()
            val price = findViewById<EditText>(R.id.input_price_p).text.toString().toDouble()
            val stock = findViewById<EditText>(R.id.input_stock_p).text.toString().toInt()
            val productList = mutableListOf<Product>()

            val product = Product(id, name, price, stock, stock != 0, distributorId)

            productList.add(product)

            if (selectedIndexItem == -1) {
                Log.e(ContentValues.TAG, "Agregar un nuevo producto")
                // Agregar un nuevo producto
                distributorsCollection.document(nameF)
                    .update("productList", productList)
                    .addOnSuccessListener {
                        response(-1)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al agregar un nuevo producto", e)
                    }
            } else {
                // Actualizar el producto existente
                distributorsCollection.document(nameF)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        val distributor = documentSnapshot.toObject(Distributor::class.java)
                        distributor?.let { distributor ->
                            val updatedProductList = distributor.productList?.toMutableList()
                            val productIndex = updatedProductList?.indexOfFirst { it.id == product.id }
                            if (productIndex != null && productIndex != -1) {
                                updatedProductList[productIndex] = product
                                distributorsCollection.document(nameF)
                                    .set(distributor.copy(productList = updatedProductList))
                                    .addOnSuccessListener {
                                        response(selectedIndexItem)
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e(TAG, "Error al actualizar el producto", e)
                                    }
                            } else {
                                Log.e(TAG, "Producto no encontrado en la lista del distribuidor")
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al obtener el distribuidor", e)
                    }
            }
        }
    }

    private fun response(position: Int) {
        val intentReturnParameters = Intent()
        intentReturnParameters.putExtra("position", position)
        setResult(RESULT_OK, intentReturnParameters)
        finish()
    }

    companion object {
        private const val TAG = "ProductsCrud"
    }
}