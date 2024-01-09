package com.example.examen01

import Model.Product
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.examen01.model.Distributor
import com.example.examenib.BaseDatosMemoria
import com.example.examenib.ListViewProducts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    val array = BaseDatosMemoria.distributorsArray
    var indexSelectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_distributors)

        val listView = findViewById<ListView>(R.id.lvListViewD)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            array
        )
        listView.adapter = adapter
        adapter.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(R.id.btn_add_distributor)
        botonAnadirListView.setOnClickListener {
            addDistributor(adapter)
        }

        registerForContextMenu(listView)
    }

    private fun addDistributor(adapter: ArrayAdapter<Distributor>) {
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
        inflater.inflate(R.menu.distributormenu, menu)
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
            R.id.mi_carros -> {
                irActividad(ListViewProducts::class.java)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto: String){
        val snack = Snackbar.make(findViewById(R.id.lvListViewD),
            texto, Snackbar.LENGTH_LONG)
        snack.show()
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}