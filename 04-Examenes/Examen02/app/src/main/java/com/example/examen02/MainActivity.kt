package com.example.examen02

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.example.examen02.model.Distributor
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val distributorsCollection = db.collection("distributors")
    private val documentNames = mutableListOf<String>()

    private var indexSelectedItem = 0
    private var distributorList = mutableListOf<Distributor>()
    private lateinit var adapter: ArrayAdapter<Distributor>

    private val callbackContent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode === Activity.RESULT_OK) {
            if (result.data != null) {
                val data = result.data
                val position = data?.getIntExtra("position", -1)
                if (position != null && position != -1) {
                    adapter.notifyDataSetChanged()
                }
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
            distributorList
        )
        listView.adapter = adapter
        adapter.notifyDataSetChanged()

        loadDistributors()

        distributorsCollection.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val documentId = document.id
                    documentNames.add(documentId)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error al obtener los documentos", exception)
            }

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedDistributor = distributorList[position]
            val explicitIntent = Intent(this, ListViewProducts::class.java)
            explicitIntent.putExtra("distributorId", selectedDistributor.distributorId)
            explicitIntent.putExtra("DistributorName", selectedDistributor.name)
            explicitIntent.putExtra("nameF", documentNames[indexSelectedItem])
            callbackContent.launch(explicitIntent)
        }

        val createDistributorButtonLv = findViewById<Button>(R.id.btn_create_distributor_lv)
        createDistributorButtonLv.setOnClickListener {
            indexSelectedItem = -1
            openActivityWithParameters(DistributorsCrud::class.java)
        }
        registerForContextMenu(listView)
    }

    override fun onResume() {
        super.onResume()
        loadDistributors()
    }

    private fun loadDistributors() {
        distributorsCollection.get()
            .addOnSuccessListener { result ->
                distributorList.clear()
                for (document in result) {
                    val distributor = document.toObject(Distributor::class.java)
                    distributorList.add(distributor)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
            }
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
                openActivityWithParameters(DistributorsCrud::class.java)
                true
            }
            R.id.mi_delete -> {
                val deletedDistributor = distributorList[indexSelectedItem]
                deleteDistributorFromFirestore(deletedDistributor)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    private fun deleteDistributorFromFirestore(distributor: Distributor) {
        val distributorId = distributor.distributorId
        if (distributorId != null) {
            distributorsCollection.document(documentNames[indexSelectedItem])
                .delete()
                .addOnSuccessListener {
                    distributorList.remove(distributor)
                    adapter.notifyDataSetChanged()
                    showSnackbar("Distribuidor eliminado correctamente")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error al eliminar el distribuidor", e)
                    showSnackbar("Error al eliminar el distribuidor")
                }
        } else {
            Log.e(TAG, "ID de distribuidor nulo")
            showSnackbar("ID de distribuidor nulo")
        }
    }


    fun showSnackbar(text: String) {
        val snack = Snackbar.make(
            findViewById(R.id.lv_distributors),
            text,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun openActivityWithParameters(clase: Class<*>) {
        val explicitIntent = Intent(this, clase)
        explicitIntent.putExtra("position", indexSelectedItem)
        if (indexSelectedItem != -1) {
            val selectedDistributor = distributorList[indexSelectedItem]
            explicitIntent.putExtra("distributorId", selectedDistributor.distributorId)
            explicitIntent.putExtra("distributorName", selectedDistributor.name)
            explicitIntent.putExtra("distributorAddress", selectedDistributor.address)
            explicitIntent.putExtra("distributorPhone", selectedDistributor.phone)
            explicitIntent.putExtra("distributorEmail", selectedDistributor.email)
            explicitIntent.putExtra("nameF", documentNames[indexSelectedItem])
        }
        callbackContent.launch(explicitIntent)
    }
}