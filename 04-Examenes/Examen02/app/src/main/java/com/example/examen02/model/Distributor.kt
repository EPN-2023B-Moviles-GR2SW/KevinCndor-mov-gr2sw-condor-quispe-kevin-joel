package com.example.examen02.model

data class Distributor(
    var distributorId: String?,
    var name: String?,
    var address: String?,
    var phone: String?,
    var email: String?,
    var productList: MutableList<Product>?
) {

    constructor() : this(null, null, null, null, null, null)

    override fun toString(): String {
        return "Nombre: ${name}\nDirección: ${address}\nTeléfono: ${phone}\nEmail: ${email}"
    }
}