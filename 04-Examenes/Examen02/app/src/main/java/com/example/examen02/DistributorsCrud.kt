package com.example.examen02


import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.examen02.model.Distributor
import com.example.examen02.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class DistributorsCrud : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val distributorsCollection = db.collection("distributors")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distributors_crud)


        // Obtener el índice y la información del distribuidor del intent
        val selectedIndexItem = intent.getIntExtra("position", -1)
        val distributorId = intent.getStringExtra("distributorId")
        val distributorName = intent.getStringExtra("distributorName")
        val distributorAddress = intent.getStringExtra("distributorAddress")
        val distributorPhone = intent.getStringExtra("distributorPhone")
        val distributorEmail = intent.getStringExtra("distributorEmail")

        // Llenar los campos con la información del distribuidor
        findViewById<EditText>(R.id.input_id_d).setText(distributorId)
        findViewById<EditText>(R.id.input_name_d).setText(distributorName)
        findViewById<EditText>(R.id.input_address_d).setText(distributorAddress)
        findViewById<EditText>(R.id.input_phone_d).setText(distributorPhone)
        findViewById<EditText>(R.id.input_email_d).setText(distributorEmail)

        val saveButton = findViewById<Button>(R.id.btn_save_distributor)
        saveButton.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id_d).text.toString()
            val name = findViewById<EditText>(R.id.input_name_d).text.toString()
            val address = findViewById<EditText>(R.id.input_address_d).text.toString()
            val phone = findViewById<EditText>(R.id.input_phone_d).text.toString()
            val email = findViewById<EditText>(R.id.input_email_d).text.toString()
            val productList = mutableListOf<Product>()

            val distributor = Distributor(id, name, address, phone, email, productList)

            if (selectedIndexItem == -1) {
                // Agregar un nuevo distribuidor
                distributorsCollection.add(distributor)
                    .addOnSuccessListener {
                        response(-1)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al agregar un nuevo distribuidor", e)
                    }
            } else if (distributorId != null) {
                val nameF = intent.getStringExtra("nameF")
                // Actualizar el distribuidor existente
                distributorsCollection.document(nameF!!)
                    .set(distributor)
                    .addOnSuccessListener {
                        response(selectedIndexItem)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al actualizar el distribuidor", e)
                    }
            } else {
                Log.e(TAG, "ID de distribuidor nulo")
            }
        }
    }

    private fun response(position: Int) {
        val intentReturnParameters = Intent()
        intentReturnParameters.putExtra("position", position)
        setResult(RESULT_OK, intentReturnParameters)
        finish()
    }
}
