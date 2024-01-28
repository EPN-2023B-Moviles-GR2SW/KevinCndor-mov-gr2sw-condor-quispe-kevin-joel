package com.example.deber02.model

class Distributor(
    private var id: String,
    private var name: String,
    private var address: String,
    private var phone: String,
    private var email:String,
    private var productos: MutableList<Product>,
){
    constructor():this("","","","", "",  mutableListOf())

    fun getId(): String{
        return id
    }
    fun getName(): String{
        return name
    }
    fun getAddres(): String{
        return address
    }
    fun getPhone(): String{
        return phone
    }
    fun getEmail(): String{
        return email
    }
    fun getProductos(): MutableList<Product>{
        return productos
    }
    fun addProduct(producto: Product){
        this.productos.add(producto)
    }
    fun removeProduct(product: Product){
        this.productos = this.productos.filter {
            it.getId() != product.getId()
        }.toMutableList()
    }
    fun setId(id: String){
        this.id = id
    }
    fun setName(name: String){
        this.name = name
    }
    fun setAddres(address: String){
        this.address = address
    }
    fun setPhone(phone: String){
        this.phone = phone
    }
    fun setEmail(Email: String){
        this.email = email
    }
    fun setProductos(productos: MutableList<Product>){
        this.productos = productos
    }
    override fun toString(): String{
        return this.name
    }
    fun getListOffStringFromData(): List<String>{
        return listOf(
            "Nombre: $name",
            "Dirección: $address",
            "Teléfono: $phone",
            "Correo: $email",
            "Productos: ${productos.map {it.getName()}}"
        )
    }
}