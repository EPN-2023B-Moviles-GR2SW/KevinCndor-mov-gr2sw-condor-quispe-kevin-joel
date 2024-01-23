package com.example.examen01

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.examen01.model.Distributor
import com.example.examen01.model.Product

class DistributorsCrud : AppCompatActivity() {

    val array = MemoryDatabase.distributorsArrayList
    var selectedIndexItem = -1
    var name: String = ""
    var address: String = ""
    var phone: String = ""
    var email: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_distributors)

        selectedIndexItem = intent.getIntExtra("position", -1)
        if(selectedIndexItem != -1){
            val inputName = findViewById<EditText>(R.id.input_name_d)
            val inputAddress = findViewById<EditText>(R.id.input_address_d)
            val inputPhone = findViewById<EditText>(R.id.input_phone_d)
            val inputEmail = findViewById<EditText>(R.id.input_email_d)

            inputName.setText(array[selectedIndexItem].name)
            inputAddress.setText(array[selectedIndexItem].address)
            inputPhone.setText(array[selectedIndexItem].phone)
            inputEmail.setText(array[selectedIndexItem].email)
        }

        val createButton = findViewById<Button>(R.id.btn_save_distributor)
        if(selectedIndexItem == -1){
            createButton
                .setOnClickListener{
                    name = findViewById<EditText>(R.id.input_name_d).text.toString()
                    address = findViewById<EditText>(R.id.input_address_d).text.toString()
                    phone = findViewById<EditText>(R.id.input_phone_d).text.toString()
                    email = findViewById<EditText>(R.id.input_email_d).text.toString()
                    val productList: ArrayList<Product> = arrayListOf()

                    array.add(
                        Distributor(
                            name,
                            address,
                            phone,
                            email,
                            productList
                        )
                    )
                    response()
                }
        }
        val updateButton = findViewById<Button>(R.id.btn_save_distributor)
        if(selectedIndexItem != -1){
            updateButton
                .setOnClickListener{
                    name = findViewById<EditText>(R.id.input_name_d).text.toString()
                    address = findViewById<EditText>(R.id.input_address_d).text.toString()
                    phone = findViewById<EditText>(R.id.input_phone_d).text.toString()
                    email = findViewById<EditText>(R.id.input_email_d).text.toString()

                    array[selectedIndexItem].name = name
                    array[selectedIndexItem].address = address
                    array[selectedIndexItem].phone = phone
                    array[selectedIndexItem].email = email
                    response()
                }
        }
    }

    private fun response(){
        val intentReturnParameters = Intent()
        intentReturnParameters.putExtra("position", selectedIndexItem)
        setResult(
            RESULT_OK,
            intentReturnParameters
        )
        finish()
    }
}