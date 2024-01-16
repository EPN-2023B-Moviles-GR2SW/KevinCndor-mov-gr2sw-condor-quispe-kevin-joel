package com.example.examen01

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.example.examen01.model.Distributor
import com.google.android.material.snackbar.Snackbar

class MainActivity : ComponentActivity() {

    val distributorArrayList = MemoryDatabase.distributorsArrayList
    var indexSelectedItem = 0
    lateinit var adapter: ArrayAdapter<Distributor>

    val callbackContent =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            if(result.resultCode === Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    adapter.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_distributors)

        val listView = findViewById<ListView>(R.id.listView_distributors)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            MemoryDatabase.distributorsArrayList
        )
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedDistributor = MemoryDatabase.distributorsArrayList[position]
            val intent = Intent(this, ListViewProducts::class.java)
            intent.putExtra("DistributorPosition",position)
            intent.putExtra("DistributorName", selectedDistributor.name)
            startActivity(intent)
        }

        val createDistributorButtonLv = findViewById<Button>(R.id.btn_create_distributor_lv)
        createDistributorButtonLv.setOnClickListener {
            indexSelectedItem = -1
            openActivityWithParameters(DistributorsCrud::class.java)
        }

        registerForContextMenu(listView)
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater= menuInflater
        inflater.inflate(R.menu.menu,menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        indexSelectedItem = position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.mi_update -> {
                openActivityWithParameters(DistributorsCrud::class.java)
                return true
            }
            R.id.mi_delete -> {
                showSnackbar("Distribuidor ${distributorArrayList[indexSelectedItem].name} eliminado con éxito")
                distributorArrayList.removeAt(indexSelectedItem)
                adapter.notifyDataSetChanged()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun showSnackbar(text: String){
        val snack = Snackbar.make(findViewById(
            R.id.lv_distributors),
            text,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun openActivityWithParameters(clase: Class<*>) {
        val explicitIntent = Intent(this, clase)
        explicitIntent.putExtra("position", indexSelectedItem)
        callbackContent.launch(explicitIntent)
    }
}
