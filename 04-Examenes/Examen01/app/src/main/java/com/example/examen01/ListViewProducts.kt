package com.example.examenib

import Model.Product
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.examen01.R
import com.example.examen01.model.Distributor
import com.google.android.material.snackbar.Snackbar

class ListViewProducts : AppCompatActivity() {
    val array = BaseDatosMemoria.distributorsArray
    var indexSelectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_products)
        val listView = findViewById<ListView>(R.id.lvListViewProducts)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            array
        )
        listView.adapter = adapter
        adapter.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(R.id.btn_add_product)
        botonAnadirListView.setOnClickListener {
            anadirCarro(adapter)
        }

        registerForContextMenu(listView)
    }

    private fun anadirCarro(adapter: ArrayAdapter<Distributor>) {
        val productList: MutableList<Product> = mutableListOf()
        array.add(
            Distributor(
                "Arca Continental",
                "Quito, Ecuador",
                "4012200",
                "arca@continental.org",
                productList
            )
        )
        adapter.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.productmenu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        indexSelectedItem = position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_c -> {
                mostrarSnackbar("${indexSelectedItem}")
                return true
            }
            R.id.mi_eliminar_c -> {
                mostrarSnackbar("${indexSelectedItem}")
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(findViewById(R.id.lvListViewProducts),
            texto, Snackbar.LENGTH_LONG)
        snack.show()
    }
}