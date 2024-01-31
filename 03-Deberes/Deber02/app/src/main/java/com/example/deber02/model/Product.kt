package com.example.deber02.model

class Product (
    private var id: String,
    private var name: String,
    private var price: Double,
    private var stock: Int,
    private var isAvailable: Boolean,
    private var distributorId: String,
){
    constructor(): this ("","",0.0,0,false, "")

    public fun getId(): String{
        return id
    }
    fun getName():String{
        return name
    }
    fun  getPrice():Double{
        return price
    }
    fun  getStock():Int{
        return stock
    }
    fun  getIsAvailable(): Boolean{
        return isAvailable
    }
    fun getDistributorId(): String{
        return distributorId
    }

    fun setDistributorId(distributorId: String){
        this.distributorId = distributorId
    }
    public fun setId(id: String){
        this.id = id
    }
    public fun setName(name: String){
        this.name = name
    }
    public fun setPrice(price: Double){
        this.price = price
    }
    public fun setStock(stock: Int){
        this.stock = stock
    }
    public fun setIsAvailable(isAvailable: Boolean){
        this.isAvailable = isAvailable
    }


    override fun toString(): String {
        return "$name"
    }
    fun getListOffStringFromData(): List<String>{
        return listOf(
            "Nombre: $name",
            "Precio: $price",
            "Unidades: $stock",
            "Disponible: $isAvailable",
        )
    }
}