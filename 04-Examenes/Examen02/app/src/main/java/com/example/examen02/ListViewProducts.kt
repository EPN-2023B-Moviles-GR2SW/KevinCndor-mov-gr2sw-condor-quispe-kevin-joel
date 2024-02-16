package com.example.examen02

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.examen02.model.Distributor
import com.example.examen02.model.Product
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class ListViewProducts : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val distributorsCollection = db.collection("distributors")

    private var nameF = ""
    private var indexSelectedItem = 0
    private var distributorPosition = -1
    private var productList = mutableListOf<Product>()
    private var distributorId = ""
    private lateinit var adapter: ArrayAdapter<Product>

    private val contentCallback = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            updateProductList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_products)
        nameF = intent.getStringExtra("nameF").toString()

        val distributorName = intent.getStringExtra("DistributorName").toString()
        distributorId = intent.getStringExtra("distributorId").toString()
        distributorPosition = intent.getIntExtra("DistributorPosition", -1)
        val txtViewProducts = findViewById<TextView>(R.id.txtV_products)
        if (distributorName != null) {
            txtViewProducts.text = distributorName.uppercase()
        }

        val listView = findViewById<ListView>(R.id.listView_products)
        registerForContextMenu(listView)

        val createProductButtonLv = findViewById<Button>(R.id.btn_create_product_lv)
        createProductButtonLv.setOnClickListener {
            indexSelectedItem = -1
            openActivityWithParameters(ProductsCrud::class.java)
        }
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            productList
        )
        listView.adapter = adapter
        updateProductList()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        indexSelectedItem = position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_update -> {
                openActivityWithParameters(ProductsCrud::class.java)
                true
            }
            R.id.mi_delete -> {
                val deletedProduct = productList.removeAt(indexSelectedItem)
                adapter.notifyDataSetChanged()
                deleteProductFromFirestore(deletedProduct)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun deleteProductFromFirestore(product: Product) {
        distributorsCollection.document(nameF)
            .update("productList", productList)
            .addOnSuccessListener {
                mostrarSnackbar("Producto eliminado con éxito")
                adapter.clear()
                adapter.addAll(productList)
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                mostrarSnackbar("Error al eliminar el producto: ${exception.message}")
                productList.add(indexSelectedItem, product)
                adapter.notifyDataSetChanged()
            }
    }

    private fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.lv_products),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun openActivityWithParameters(clase: Class<*>) {
        val explicitIntent = Intent(this, clase)
        explicitIntent.putExtra("position", indexSelectedItem)
        explicitIntent.putExtra("distributorPosition", distributorPosition)
        explicitIntent.putExtra("distributorId", getDistributorId())
        explicitIntent.putExtra("nameF", nameF)
        if (indexSelectedItem != -1) {
            val selectedProduct = productList[indexSelectedItem]
            explicitIntent.putExtra("productId", selectedProduct.id)
            explicitIntent.putExtra("productName", selectedProduct.name)
            explicitIntent.putExtra("productPrice", selectedProduct.price)
            explicitIntent.putExtra("productStock", selectedProduct.stock)
            explicitIntent.putExtra("productIsAvailable", selectedProduct.isAvailable)
            explicitIntent.putExtra("editar", 0)
        }
        contentCallback.launch(explicitIntent)
    }

    private fun updateProductList() {
        distributorsCollection.document(nameF).get()
            .addOnSuccessListener { documentSnapshot ->
                val distributor = documentSnapshot.toObject(Distributor::class.java)
                if (distributor != null) {
                    productList = distributor.productList!!
                    adapter.clear()
                    adapter.addAll(productList)
                }
            }
            .addOnFailureListener { exception ->
            }
    }

    private fun getDistributorId(): String {
        return distributorId
    }
}