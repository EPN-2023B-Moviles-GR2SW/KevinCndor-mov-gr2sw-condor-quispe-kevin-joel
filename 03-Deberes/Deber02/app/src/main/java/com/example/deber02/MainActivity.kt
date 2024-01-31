package com.example.deber02

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
import androidx.appcompat.app.AlertDialog
import com.example.deber02.db.DSqliteHelper
import com.example.deber02.db.Database
import com.example.deber02.db.PSqliteHelper
import com.example.deber02.model.Distributor

class MainActivity : AppCompatActivity() {
    private var distributors: ArrayList<Distributor>? = null
    var selectedItemId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Database.distribuidores = DSqliteHelper(this)
        Database.productos = PSqliteHelper(this)
        loadDistributors()
        val btnCreate = findViewById<Button>(R.id.btn_distributors)
        btnCreate.setOnClickListener {
            goToActivity(CreateDistributor::class.java)
        }
    }


    override fun onResume() {
        super.onResume()
        loadDistributors()
    }

    private fun loadDistributors() {
        val listView = findViewById<ListView>(
            R.id.lv_distributors
        )
        distributors = Database.distribuidores!!.getAll()
        if (distributors != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                distributors!!
            )
            listView.adapter = adapter
            adapter.notifyDataSetChanged()
            registerForContextMenu(listView)
        }

    }


    private fun goToActivity(activity: Class<*>, params: Bundle? = null) {
        val intent = Intent(this, activity)
        if (params != null) {
            intent.putExtras(params)
        }
        startActivity(intent)
    }

    private fun showConfirmDeleteDialog(distributor: Distributor) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Estás seguro de eliminar el distribuidor: ${distributor.getName()}?")
        builder.setMessage("Una vez eliminado no se podrá recuperar.")
        builder.setPositiveButton("Sí") { _, _ ->
            val removed = distributors!!.removeAt(selectedItemId)
            Database.distribuidores!!.remove(removed.getId())
            loadDistributors()
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
        inflater.inflate(R.menu.menu_distributors, menu)

        //position
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        selectedItemId = position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val distributor = distributors!![selectedItemId]
        return when(item.itemId) {
            R.id.mi_show_products -> {
                "Hacer algo con: ${selectedItemId}"
                if (distributors == null) return false

                val params = Bundle()
                params.putString("id", distributor.getId())
                params.putString("name", distributor.getName())
                params.putString("address", distributor.getAddres())
                params.putString("phone", distributor.getPhone())
                params.putString("email", distributor.getEmail())

                goToActivity(ProductsActivity::class.java, params)
                return true
            }
            R.id.mi_update -> {
                "Hacer algo con: ${selectedItemId}"
                val params = Bundle()
                params.putString("id", distributor.getId())
                params.putString("name", distributor.getName())
                params.putString("address", distributor.getAddres())
                params.putString("phone", distributor.getPhone())
                params.putString("email", distributor.getEmail())

                goToActivity(UpdateDistributor::class.java, params)
                return true
            }
            R.id.mi_delete -> {
                showConfirmDeleteDialog(distributor)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}