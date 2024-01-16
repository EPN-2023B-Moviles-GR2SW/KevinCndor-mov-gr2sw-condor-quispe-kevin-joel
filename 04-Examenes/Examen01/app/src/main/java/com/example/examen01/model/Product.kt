package com.example.examen01.model

data class Product(
    val id: Int,
    var name: String,
    var price:Double,
    var stock: Int,
    var isAvailable: Boolean
) {
    override fun toString(): String {
        return "Nombre: ${name}\nPrecio: ${price}\nDisponible: ${if (isAvailable)"Si" else "No"}"
    }
}