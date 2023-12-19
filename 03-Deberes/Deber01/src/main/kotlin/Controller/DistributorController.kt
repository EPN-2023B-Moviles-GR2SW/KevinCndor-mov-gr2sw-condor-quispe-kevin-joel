package Controller

import Model.Distributor
import Model.Product
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class DistributorController(private val file: File) {
    private val objectMapper = jacksonObjectMapper()
    private val distributors: MutableList<Distributor> = mutableListOf()

    init {
        loadDistributors()
    }

    private fun loadDistributors(){
        if(file.exists()){
            val distributorsJson = file.readText()
            distributors.addAll(objectMapper.readValue<List<Distributor>>(distributorsJson))
        }
    }

    //CRUD methods for distributor.
    fun createDistributor(distributor: Distributor){
        distributors.add(distributor)
        saveDistributor()
    }

    fun readDistributors(): List<Distributor> {
        return distributors.toList()
    }

    fun printDistributor(distributor: Distributor) {
        println("Nombre: ${distributor.name}")
        println("Dirección: ${distributor.address}")
        println("Telefono: ${distributor.phone}")
        println("Correo: ${distributor.email}")
        println("Productos:")
        distributor.productsList.forEach {
            println("  - ${it.name} - Precio: ${it.price} - Stock: ${it.stock}")
        }
        println()
    }

    fun printDistributors(distributors: List<Distributor>) {
        distributors.forEach {
            printDistributor(it)
        }
    }

    fun updateDistributor(name: String, updatedDistributor: Distributor){
        val distributorIndex = distributors.indexOfFirst { it.name == name}
        if(distributorIndex != -1){
            distributors[distributorIndex] = updatedDistributor
            saveDistributor()
        }else{
            println("El Distribuidor no existe.")
        }
    }

    fun deleteDistributor(name: String) {
        val distributorIndex = distributors.indexOfFirst { it.name == name }
        if (distributorIndex != -1) {
            distributors.removeAt(distributorIndex)
            saveDistributor()
            println("Distribuidor eliminado exitosamente.")
        } else {
            println("El Distribuidor no existe.")
        }
    }


    private fun saveDistributor(){
        val distributorsJson = objectMapper.writeValueAsString(distributors)
        file.writeText(distributorsJson)
    }

    fun printProduct(product: Product) {
        println("ID: ${product.id}")
        println("Nombre: ${product.name}")
        println("Precio: ${product.price}")
        println("Stock: ${product.stock}")
        println("Disponible: ${product.isAvailable}")
        println()
    }

    fun printProducts(products: List<Product>) {
        products.forEach {
            printProduct(it)
        }
    }

}
