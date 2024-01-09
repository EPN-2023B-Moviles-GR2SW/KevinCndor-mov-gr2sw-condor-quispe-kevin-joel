package com.example.examen01.model

import Model.Product


data class Distributor (
    val name: String,
    val address: String,
    val phone: String,
    val email: String,
    val productsList: MutableList<Product>
){
    fun addProduct(product: Product){
        productsList.add(product)
    }

    override fun toString(): String {
        return "Nombre: ${name} - dirección: ${address}"
    }
}