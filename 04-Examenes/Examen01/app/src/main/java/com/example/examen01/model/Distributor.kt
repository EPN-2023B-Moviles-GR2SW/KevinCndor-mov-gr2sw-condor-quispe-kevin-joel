package com.example.examen01.model

data class Distributor(
    var name: String,
    var address: String,
    var phone: String,
    var email:String,
    var productList: ArrayList<Product>
) {
    fun addProduct(product: Product){
        productList.add(product)
    }

    override fun toString(): String {
        return "Nombre: ${name}\nDirección: ${address}\nTeléfono: ${phone}\nEmail: ${email}"
    }
}