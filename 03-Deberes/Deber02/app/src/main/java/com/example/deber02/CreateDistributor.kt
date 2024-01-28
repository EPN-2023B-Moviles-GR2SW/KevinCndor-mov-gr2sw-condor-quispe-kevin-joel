package com.example.deber02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.deber02.db.Database
import com.example.deber02.dto.DistributorDto
import com.example.deber02.model.Distributor

class CreateDistributor : AppCompatActivity() {
    private var distributors: ArrayList<Distributor>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_distributor)

        distributors = Database.distribuidores!!.getAll()
        val goBackButton = findViewById<ImageButton>(R.id.btn_go_back)
        goBackButton.setOnClickListener{
            finish()
        }
        val saveNewDistributorButton = findViewById<Button>(R.id.btn_createDistributor)
        saveNewDistributorButton.setOnClickListener {
            createDistributor()
        }
    }

    private fun createDistributor(){
        val inputName = findViewById<EditText>(R.id.txt_distributorName)
        val inputAddress = findViewById<EditText>(R.id.txt_distributorAddress)
        val inputPhone = findViewById<EditText>(R.id.txt_distributorPhone)
        val inputEmail = findViewById<EditText>(R.id.txt_distributorEmail)

        val name = inputName.text.toString()
        val address = inputAddress.text.toString()
        val phone = inputPhone.text.toString()
        val email = inputEmail.text.toString()

        val newDistributor = DistributorDto(
            name,
            address,
            phone,
            email
        )
        Database.distribuidores!!.create(newDistributor)
        finish()
    }
}