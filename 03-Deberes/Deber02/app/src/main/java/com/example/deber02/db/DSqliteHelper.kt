package com.example.deber02.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.example.deber02.dto.DistributorDto
import com.example.deber02.model.Distributor

class DSqliteHelper(
    context: Context
): SQLiteOpenHelper(
    context,
    "distribuidor",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCreateDistributorTable =
            """
                CREATE TABLE DISTRIBUTOR(
                    ID VARCHAR(40) PRIMARY KEY,
                    NAME VARCHAR(50),
                    ADDRESS VARCHAR(100),
                    PHONE VARCHAR(10),
                    EMAIL VARCHAR(50)
                )
            """.trimIndent()

        db?.execSQL(scriptCreateDistributorTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    private fun generateId(): String{
        val id = (0..1000000).random()
        return id.toString()
    }

    fun getAll(): ArrayList<Distributor>{
        val readableDatabase = readableDatabase
        val script = "SELECT * FROM DISTRIBUTOR".trimIndent()
        val result = readableDatabase.rawQuery(
            script,
            null
        )

        val exists = result.moveToFirst()
        val distributors = arrayListOf<Distributor>()
        if(exists){
            do{
                val id = result.getString(0)
                val name = result.getString(1)
                val addres = result.getString(2)
                val phone = result.getString(3)
                val email = result.getString(4)
                if(id != null){
                    val distributor = Distributor(id, name, addres, phone,email, mutableListOf())
                    distributors.add(distributor)
                }
            } while (result.moveToNext())
        }
        result.close()
        readableDatabase.close()
        return distributors
    }

    fun getOne(id: String): Distributor {
        val readableDatabase = readableDatabase
        val script = "SELECT * FROM DISTRIBUTOR WHERE ID = ?".trimIndent()
        val result = readableDatabase.rawQuery(
            script,
            arrayOf(id.toString())
        )

        val exists = result.moveToFirst()
        val found = Distributor("", "", "", "", "", mutableListOf())
        if (exists) {
            do {
                val id = result.getString(0)
                val name = result.getString(1)
                val addres = result.getString(2)
                val phone = result.getString(3)
                val email = result.getString(4)
                if (id != null) {
                    found.setId(id)
                    found.setName(name)
                    found.setAddres(addres)
                    found.setPhone(phone)
                    found.setEmail(email)
                }
            } while (result.moveToNext())
        }
        result.close()
        readableDatabase.close()
        return found
    }

    fun create(data: DistributorDto): Boolean{
        val writebleDatabase = writableDatabase
        val values = ContentValues()

        values.put("ID", generateId())
        values.put("NAME", data.name)
        values.put("ADDRESS", data.address)
        values.put("PHONE", data.phone)
        values.put("EMAIL", data.email)
        val result = writableDatabase.insert(
            "DISTRIBUTOR",
            null,
            values
        )

        writebleDatabase.close()
        return result.toInt() != -1
    }

    fun update(id: String, data: DistributorDto): Boolean{
        val writableDatabase = writableDatabase
        val values = ContentValues()

        values.put("ID", generateId())
        values.put("NAME", data.name)
        values.put("ADDRESS", data.address)
        values.put("PHONE", data.phone)
        values.put("EMAIL", data.email)

        val result = writableDatabase.update(
            "DISTRIBUTOR",
            values,
            "ID = ?",
            arrayOf(id.toString())
        )
        writableDatabase.close()
        return result != -1
    }

    fun remove(id: String): Boolean {
        val writableDatabase = writableDatabase
        val result = writableDatabase.delete(
            "DISTRIBUTOR",
            "ID = ?",
            arrayOf(id.toString())
        )
        writableDatabase.close()
        return result != -1
    }
}