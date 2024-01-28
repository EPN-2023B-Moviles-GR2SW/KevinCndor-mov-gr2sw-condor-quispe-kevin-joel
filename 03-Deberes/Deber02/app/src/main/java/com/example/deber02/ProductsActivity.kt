package com.example.deber02

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.deber02.db.Database
import com.example.deber02.model.Product

class ProductsActivity : AppCompatActivity() {
    private var productos: ArrayList<Product>? = null
    var selectedProductId = ""
    var selectedItemId = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        selectedProductId = intent.getStringExtra("id").toString()
        println("Distributor Id: $selectedProductId")
        loadProducts(selectedProductId)
        val goBackButton = findViewById<ImageButton>(R.id.btn_go_back_to_distributors)

        goBackButton.setOnClickListener {
            finish()
        }
        val createProductButton = findViewById<Button>(R.id.btn_create_product)
        createProductButton.setOnClickListener {
            goToActivity(CreateProduct::class.java, Bundle().apply {
                val distribuidores = Database.distribuidores!!.getOne(selectedProductId)
                if (distribuidores.getId() == selectedProductId) {
                    putString("productId", selectedProductId)
                    putString("distributorId", distribuidores.getId())
                    putString("distributorName", distribuidores.getName())
                }
            })
        }

    }

    override fun onResume() {
        super.onResume()

        loadProducts(selectedProductId)
    }

    private fun goToActivity(activity: Class<*>, params: Bundle? = null) {
        val intent = Intent(this, activity)
        if (params != null) {
            intent.putExtras(params)
        }
        startActivity(intent)
    }

    private fun loadProducts(distributorId: String) {
        if (distributorId != "") {
            val distributor = Database.distribuidores!!.getOne(distributorId)

            if (distributor.getId() == distributorId) {
                productos = Database.productos!!.getAllByDistributor(distributorId)

                val tvTitle = findViewById<TextView>(R.id.tv_distributor)
                tvTitle.text = distributor.getName()
                val productList = findViewById<ListView>(R.id.lv_products)
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    productos!!
                )
                productList.adapter = adapter
                adapter.notifyDataSetChanged()
                registerForContextMenu(productList)
            }

        }
    }

    private fun showConfirmDialog(product: Product) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Deseas eliminar el producto ${product.getName()}?")
        builder.setMessage("Una vez eliminado, no lo podrás recuperar")
        builder.setPositiveButton("Sí, eliminar") { dialog, which ->
            Database.productos!!.remove(product.getId())
            loadProducts(selectedProductId)
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(
            R.menu.menu_products,
            menu
        )

        // position
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        selectedItemId = position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val selectedProduct = productos!![selectedItemId]
        return when(item.itemId) {
            R.id.mi_edit_products -> {
                val distributor = Database.distribuidores!!.getOne(selectedProductId)
                goToActivity(
                    UpdateProduct::class.java,
                    Bundle().apply {
                        putString("distributorId", selectedProductId)
                        putString("distributorName", distributor.getName())
                        putString("productId", selectedProduct.getId())
                        putString("name", selectedProduct.getName())
                        putDouble("price", selectedProduct.getPrice())
                        putInt("stock", selectedProduct.getStock())
                        putBoolean("isAvailable", selectedProduct.getIsAvailable())
                    }
                )
                true
            }
            R.id.mi_delete_products -> {
                showConfirmDialog(selectedProduct)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}