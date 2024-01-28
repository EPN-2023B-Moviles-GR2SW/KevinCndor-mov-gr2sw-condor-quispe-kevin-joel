package com.example.deber02.db

class Database {
    companion object{
        var productos: PSqliteHelper? = null
        var distribuidores: DSqliteHelper? = null
    }
}