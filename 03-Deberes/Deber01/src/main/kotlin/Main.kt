import Controller.DistributorController
import Model.Distributor
import Model.Product
import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val filePath = "src/main/kotlin/File/distributorsFile.json"
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

        when (scanner.nextInt()) {
            1 -> {
                val distributors = distributorController.readDistributors()
                distributorController.printDistributors(distributors)
                waiting()
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
                waiting()
            }
            3 -> {
                println("Ingrese el nombre del Distribuidor a actualizar")
                val updateName = scanner.next()
                val foundDistributor =
                    distributorController.readDistributors().find { it.name.uppercase() == updateName.uppercase() }
                if (foundDistributor != null) {
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
                    waiting()
                } else {
                    print("El distribuidor no existe.")
                    waiting()
                }
            }
            4 -> {
                println("Ingrese el nombre del distribuidor a eliminar")
                val name = scanner.next()
                distributorController.deleteDistributor(name)
            }
            5 -> {
                println("Ingrese el nombre del distribuidor al que desea agregar productos")
                val distributorName = scanner.next()

                val distributor = distributorController.readDistributors()
                    .find { it.name == distributorName }

                if (distributor != null) {
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
                    if (stock != null) {
                        isAvailable = true
                    }

                    val newProduct = Product(id, name, price, stock, isAvailable)
                    distributor.addProduct(newProduct)
                    println("Producto agregado exitosamente.")
                }
            }
            6 -> {
                println("Ingrese el nombre del distribuidor del que desea ver los productos: ")
                val distributorName = scanner.next()
                val distributor = distributorController.readDistributors()
                    .find { it.name == distributorName }
                if (distributor != null) {
                    val products = distributor.readProducts()
                    products.forEach {
                        distributorController.printProducts(products)
                    }
                    waiting()
                } else {
                    println("El distribuidor no existe")
                    waiting()
                }
            }
            7 -> {
                println("Ingrese el nombre del distribuidor del que desea actualizar el producto: ")
                val distributorName = scanner.next()
                val distributor = distributorController.readDistributors()
                    .find { it.name == distributorName }
                if (distributor != null) {
                    println("Ingrese el nombre del producto a actualizar: ")
                    val productToUpdate = scanner.next()
                    val products = distributor.readProducts()
                    val product = products.find { it.name == productToUpdate }
                    if (product != null) {
                        print("Ingrese el nuevo precio del producto: ")
                        val newPrice = scanner.nextDouble()
                        val updatedProduct = Product(
                            product.id, product.name, newPrice,
                            product.stock, product.isAvailable
                        )
                        distributor.updateProduct(distributorName, updatedProduct)
                        distributorController.updateDistributor(distributorName, distributor)
                        println("Producto actualizado correctamente!")
                        waiting()
                    } else {
                        println("No se encontró el producto")
                        waiting()
                    }
                } else {
                    println("El Distribuidor no existe")
                    waiting()
                }
            }
            8 -> {
                println("Ingrese el nombre del distribuidor del que desea eliminar el producto: ")
                val distributorName = scanner.next()
                val distributor = distributorController.readDistributors()
                    .find { it.name == distributorName }
                if (distributor != null) {
                    println("Ingrese el nombre del producto a eliminar: ")
                    val productToUpdate = scanner.next()
                    val products = distributor.readProducts()
                    val product = products.find { it.name == productToUpdate }
                    if (product != null) {
                        distributor.deleteProduct(productToUpdate)
                        distributorController.updateDistributor(distributorName, distributor)
                        println("Producto eliminado exitosamente!")
                        waiting()
                    } else {
                        println("No se encontró el producto")
                        waiting()
                    }
                } else {
                    println("El Distribuidor no existe")
                    waiting()
                }
            }
            9 -> {
                println("Saliendo de la aplicación!")
                return
            }
            else -> {
                println("Opción incorrecta, ingrese un número válido")
                waiting()
            }
        }
    }
}

fun waiting() {
    println("\nPresione Enter para continuar...")
    readLine()
}

