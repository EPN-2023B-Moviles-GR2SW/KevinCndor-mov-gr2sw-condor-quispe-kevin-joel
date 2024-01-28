package com.example.deber02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.deber02.db.Database
import com.example.deber02.dto.DistributorDto

class UpdateDistributor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_distributor)

        loadDataInEditText()
        val goBackButton = findViewById<ImageButton>(R.id.btn_go_back)
        goBackButton.setOnClickListener {
            finish()
        }
        val saveUpdateDataButton = findViewById<Button>(R.id.btn_update_distributor)

        saveUpdateDataButton.setOnClickListener {
            saveUpdateData()
        }
    }

    private fun loadDataInEditText() {
        val inputName = findViewById<EditText>(R.id.txt_distributor_update_name)
        val inputAddress = findViewById<EditText>(R.id.txt_distributor_update_address)
        val inputPhone = findViewById<EditText>(R.id.txt_distributor_update_phone)
        val inputEmail = findViewById<EditText>(R.id.txt_distributor_update_email)

        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val address = intent.getStringExtra("address")
        val email = intent.getStringExtra("email")

        inputName.setText(name)
        inputPhone.setText(phone)
        inputAddress.setText(address)
        inputEmail.setText(email)
    }

    private fun saveUpdateData() {
        val inputName = findViewById<EditText>(R.id.txt_distributor_update_name)
        val inputAddress = findViewById<EditText>(R.id.txt_distributor_update_address)
        val inputPhone = findViewById<EditText>(R.id.txt_distributor_update_phone)
        val inputEmail = findViewById<EditText>(R.id.txt_distributor_update_email)

        val name = inputName.text.toString()
        val phone = inputPhone.text.toString()
        val address = inputAddress.text.toString()
        val email = inputEmail.text.toString()

        val supermercadoId = intent.getStringExtra("id")

        val changes = DistributorDto(
            name = name,
            address = address,
            phone = phone,
            email = email
        )

        Database.distribuidores!!.update(supermercadoId!!, changes)
        finish()
    }
}