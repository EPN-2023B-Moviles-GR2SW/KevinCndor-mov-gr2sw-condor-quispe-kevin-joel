package com.example.deber02.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deber02.dto.ProductDto
import com.example.deber02.model.Product

class PSqliteHelper(
    context: Context
): SQLiteOpenHelper(
    context,
    "producto",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCreateProductoTable =
            """
                CREATE TABLE PRODUCT(
                    ID VARCHAR(40) PRIMARY KEY,
                    NAME VARCHAR(50),
                    PRICE REAL,
                    STOCK INTEGER,
                    ISAVAILABLE INTEGER,
                    DISTRIBUTORID VARCHAR(40),
                    FOREIGN KEY(DISTRIBUTORID) REFERENCES DISTRIBUTOR(ID)
                )
            """.trimIndent()

        db?.execSQL(scriptCreateProductoTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    private fun generateId(): String {
        val id = (0..1000000).random()
        return id.toString()
    }

    fun getAll(): ArrayList<Product> {
        val readableDatabase = readableDatabase
        val script = "SELECT * FROM PRODUCT".trimIndent()
        val result = readableDatabase.rawQuery(
            script,
            null
        )

        val exists = result.moveToFirst()
        val productos = arrayListOf<Product>()
        if (exists) {
            do {
                val id = result.getString(0)
                val name = result.getString(1)
                val price = result.getDouble(2)
                val stock = result.getInt(3)
                val isAvailable = result.getInt(4) == 1
                val distributorId = result.getString(5)

                if (id != null) {
                    val products = Product(
                        id,
                        name,
                        price,
                        stock,
                        isAvailable,
                        distributorId
                    )
                    productos.add(products)
                }
            } while (result.moveToNext())
        }
        return productos
    }

    fun getAllByDistributor(distributorId: String): ArrayList<Product> {
        val readableDatabase = readableDatabase
        val script = "SELECT * FROM PRODUCT WHERE DISTRIBUTORID = ?".trimIndent()
        val result = readableDatabase.rawQuery(
            script,
            arrayOf(distributorId.toString())
        )

        val exists = result.moveToFirst()
        val productos = arrayListOf<Product>()

        if (exists) {
            do {
                val id = result.getString(0)
                val name = result.getString(1)
                val price = result.getDouble(2)
                val stock = result.getInt(3)
                val isAvailable = result.getInt(4) == 1
                val distributorId = result.getString(5)

                if (id != null) {
                    val product = Product(
                        id,
                        name,
                        price,
                        stock,
                        isAvailable,
                        distributorId
                    )
                    productos.add(product)
                }
            } while (result.moveToNext())
        }
        result.close()
        readableDatabase.close()
        return productos
    }

    fun getOne(id: String): Product {
        val readableDatabase = readableDatabase
        val script = "SELECT * FROM PRODUCT WHERE ID = ?".trimIndent()
        val result = readableDatabase.rawQuery(
            script,
            arrayOf(id.toString())
        )

        val exists = result.moveToFirst()
        val product = Product()
        if (exists) {
            val id = result.getString(0)
            val name = result.getString(1)
            val price = result.getDouble(2)
            val stock = result.getInt(3)
            val isAvailable = result.getInt(4) == 1
            val distributorId = result.getString(5)

            if (id != null) {
                product.setId(id)
                product.setName(name)
                product.setPrice(price)
                product.setStock(stock)
                product.setIsAvailable(isAvailable)
                product.setDistributorId(distributorId)
            }
        }
        result.close()
        readableDatabase.close()
        return product
    }

    fun create(data: ProductDto): Boolean {
        val writableDatabase = writableDatabase
        val values = ContentValues()

        values.put("ID", generateId())
        values.put("NAME", data.name)
        values.put("PRICE", data.price)
        values.put("STOCK", data.stock)
        values.put("ISAVAILABLE", data.isAvailable)
        values.put("DISTRIBUTORID", data.distributorId)

        val result = writableDatabase.insert(
            "PRODUCT",
            null,
            values
        )

        writableDatabase.close()
        return result.toInt() != -1
    }

    fun update(id: String, data: ProductDto): Boolean {
        val writableDatabase = writableDatabase
        val values = ContentValues()

        values.put("ID", generateId())
        values.put("NAME", data.name)
        values.put("PRICE", data.price)
        values.put("STOCK", data.stock)
        values.put("ISAVAILABLE", data.isAvailable)
        values.put("DISTRIBUTORID", data.distributorId)

        val result = writableDatabase.update(
            "PRODUCT",
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
            "PRODUCT",
            "ID = ?",
            arrayOf(id.toString())
        )

        writableDatabase.close()
        return result != -1
    }
}