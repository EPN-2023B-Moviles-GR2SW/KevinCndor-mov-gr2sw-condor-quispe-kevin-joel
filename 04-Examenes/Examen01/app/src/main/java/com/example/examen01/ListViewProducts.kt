package com.example.examen01

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
import com.example.examen01.model.Product
import com.google.android.material.snackbar.Snackbar

class ListViewProducts : AppCompatActivity() {

    var indexSelectedItem = 0
    var arrayIndex = 0
    var productList = arrayListOf<Product>()
    var distributorPosition = -1
    lateinit var adapter: ArrayAdapter<Product>

    val contentCallback= registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode === Activity.RESULT_OK){
            if (result.data != null){
                val data = result.data
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_products)

        val distributorName = intent.getStringExtra("DistributorName").toString()
        distributorPosition = intent.getIntExtra("DistributorPosition",-1)
        val txtViewProducts = findViewById<TextView>(R.id.txtV_products)
        if (distributorName != null) {
            txtViewProducts.text = distributorName.uppercase()
        }
        productList = MemoryDatabase.getProductsForDistributorByName(distributorName) as ArrayList<Product>
        val listView = findViewById<ListView>(R.id.listView_products)

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            productList
        )
        listView.adapter = adapter

        val createProductButtonLv = findViewById<Button>(R.id.btn_create_product_lv)
        createProductButtonLv.setOnClickListener{
            indexSelectedItem = -1
            openActivityWithParameters(ProductsCrud::class.java)
        }
        registerForContextMenu(listView)
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
        return when (item.itemId){
            R.id.mi_update ->{
                openActivityWithParameters(ProductsCrud::class.java)
                return true
            }
            R.id.mi_delete ->{
                mostrarSnackbar("Producto ${productList[indexSelectedItem].name} eliminado con éxito")
                productList.removeAt(indexSelectedItem)
                adapter.notifyDataSetChanged()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun mostrarSnackbar(texto: String){
        val snack = Snackbar.make(findViewById(
            R.id.lv_products),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun openActivityWithParameters(clase: Class<*>) {
        val explicitIntent = Intent(this, clase)
        explicitIntent.putExtra("position", indexSelectedItem)
        explicitIntent.putExtra("arrayIndex", arrayIndex)
        explicitIntent.putExtra("distributorPosition", distributorPosition)
        contentCallback.launch(explicitIntent)
    }
}