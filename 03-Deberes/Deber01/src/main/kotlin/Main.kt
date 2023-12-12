import Controller.DistributorController
import Model.Distributor
import Model.Product
import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val filePath = "src/main/kotlin/files/distributorsFile.json"
    val distributorFile = File(filePath)
    val distributorController = DistributorController(distributorFile)
    val scanner = Scanner(System.`in`)

    while (true) {
        println("         Gestor de Inventario         ")
        println("1. Ver Distribuidores")
        println("2. Agregar Distribuidor")
        println("3. Actualizar Distribuidor")
        println("4. Eliminar Distribuidor")
        println("5. Agregar Producto a un Distribuidor")
        println("6. Ver Productos de un Distribuidor")
        println("7. Actualizar Producto de un Distribuidor")
        println("8. Eliminar Producto de un Distribuidor")
        println("9. Salir")
        println("Escoja una opción:")
    }

    when (scanner.nextInt()){
        1 -> {
            val distributors = distributorController.readDistributors()
            distributorController.printDistributors(distributors)
            wait()
        }
        2 -> {
            println("Ingrese los detalles del distribuidor:")
            print("Nombre: ")
            val name = scanner.next()
            print("Dirección: ")
            val addres = scanner.next()
            print("Teléfono: ")
            val phone = scanner.next()
            print("Correo: ")
            val email = scanner.next()

            // Empty list
            val productsList: MutableList<Product> = mutableListOf()

            val newDistributor = Distributor(name, addres, phone, email, productsList)
            distributorController.createDistributor(newDistributor)
            println("Distribuidor creado exitosamente.")
            wait()
        }
        3 -> {
            println("Ingrese el nombre del Distribuidor a actualizar")
            val updateName = scanner.next()
            val foundDistributor =
                    distributorController.readDistributors().find { it.name.uppercase() == updateName.uppercase() }
            if (foundDistributor != null){
                print("Ingrese la nueva direccion: ")
                val newAddress = scanner.next()
                print("\nIngrese el nuevo telefono:")
                val newPhone = scanner.next()
                print("Ingrese el nuevo correo:")
                val newEmail = scanner.next()

                val updatedDistributor = Distributor(
                    foundDistributor.name,
                    newAddress,
                    newPhone,
                    newEmail,
                    foundDistributor.productsList
                )
                distributorController.updateDistributor(updateName, updatedDistributor)
                println("Distribuidor actualizado con exito!")
                wait()
            }else{
                print("El distribuidor no existe.")
                wait()
            }
        }
        4 -> {
            println("Ingrese el nombre del Distribuidor a eliminar")
            val name = scanner.next()
            distributorController.deleteDistributor(name)
        }
        5 -> {
            println("Ingrese el nombre del Distribuidor al que desea agregar productos")
            val distributorName = scanner.next()

            val distributor = distributorController.readDistributors()
                .find { it.name == distributorName }

            if(distributor != null){
                println("Ingrese los detalles del producto:")
                print("ID: ")
                val id = scanner.nextInt()
                print("Nombre: ")
                val name = scanner.next()
                print("Precio: ")
                val price = scanner.nextDouble()
                print("Stock: ")
                val stock = scanner.nextInt()
                var isAvailable: Boolean = false
                if(stock != null){
                    isAvailable = true
                }

                val newProduct = Product(id,name,price,stock, isAvailable)
                distributor.addProduct(newProduct)
                println("Producto agregado exitosamente.")
            }
        }
        6 ->{
            println()
        }

    }
}

fun wait() {
    println("\nPresione Enter para continuar...")
    readLine()
}