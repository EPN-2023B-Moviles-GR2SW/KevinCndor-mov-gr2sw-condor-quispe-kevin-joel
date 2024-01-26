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
        println("1. Distribuidores")
        println("2. Productos")
        println("3. Salir")
        println("Escoja una opción:")

        when (scanner.nextInt()) {
            1 ->{
                println("         Gestor de Inventario         ")
                println("1. Ver Distribuidores")
                println("2. Agregar Distribuidor")
                println("3. Actualizar Distribuidor")
                println("4. Eliminar Distribuidor")
                println("5. Salir")
                println("Escoja una opción:")
                when(scanner.nextInt()){
                    1 -> {
                        val distributors = distributorController.readDistributors()
                        distributorController.printDistributors(distributors)
                        waiting()
                    }
                    2 -> {
                        scanner.nextLine()
                        println("Ingrese los detalles del distribuidor:")
                        print("Nombre: ")
                        val name = scanner.nextLine()
                        print("Dirección: ")
                        val addres = scanner.nextLine()
                        print("Teléfono: ")
                        val phone = scanner.nextLine()
                        print("Correo: ")
                        val email = scanner.nextLine()

                        // Empty list
                        val productsList: MutableList<Product> = mutableListOf()

                        val newDistributor = Distributor(name, addres, phone, email, productsList)
                        distributorController.createDistributor(newDistributor)
                        println("Distribuidor creado exitosamente.")
                        waiting()
                    }
                    3 -> {
                        scanner.nextLine()
                        println("Ingrese el nombre del Distribuidor a actualizar")
                        val updateName = scanner.nextLine()
                        val foundDistributor =
                            distributorController.readDistributors().find { it.name.uppercase() == updateName.uppercase() }
                        if (foundDistributor != null) {
                            print("Ingrese la nueva direccion: ")
                            val newAddress = scanner.nextLine()
                            print("Ingrese el nuevo telefono:")
                            val newPhone = scanner.nextLine()
                            print("Ingrese el nuevo correo:")
                            val newEmail = scanner.nextLine()

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
                        scanner.nextLine()
                        println("Ingrese el nombre del distribuidor a eliminar")
                        val name = scanner.nextLine()
                        distributorController.deleteDistributor(name)
                    }
                    5 -> {
                        println("Saliendo de la aplicación!")
                        return
                    }
                    else -> {
                        println("Opción incorrecta, ingrese un número válido")
                        waiting()
                    }
                }
            }
            2 ->{
                println("         Gestor de Inventario         ")
                println("1. Ver Productos de un Distribuidor")
                println("2. Agregar Producto a un Distribuidor")
                println("3. Actualizar Producto de un Distribuidor")
                println("4. Eliminar Producto de un Distribuidor")
                println("5. Salir")
                println("Escoja una opción:")
                when (scanner.nextInt()){
                    1 -> {
                        scanner.nextLine()
                        println("Ingrese el nombre del distribuidor del que desea ver los productos: ")
                        val distributorName = scanner.nextLine()
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
                    2 -> {
                        scanner.nextLine()
                        println("Ingrese el nombre del distribuidor al que desea agregar productos")
                        val distributorName = scanner.nextLine()

                        val distributor = distributorController.readDistributors()
                            .find { it.name == distributorName }

                        if (distributor != null) {
                            println("Ingrese los detalles del producto:")
                            print("ID: ")
                            val id = scanner.nextInt()
                            scanner.nextLine()
                            print("Nombre: ")
                            val name = scanner.nextLine()
                            print("Precio: ")
                            val price = scanner.nextDouble()
                            print("Stock: ")
                            val stock = scanner.nextInt()
                            var isAvailable: Boolean = false
                            if (stock != null || stock != 0) {
                                isAvailable = true
                            }

                            val newProduct = Product(id, name, price, stock, isAvailable)
                            distributor.addProduct(newProduct)
                            println("Producto agregado exitosamente.")
                        }
                    }
                    3 -> {
                        scanner.nextLine()
                        println("Ingrese el nombre del distribuidor del que desea actualizar el producto: ")
                        val distributorName = scanner.nextLine()
                        val distributor = distributorController.readDistributors()
                            .find { it.name == distributorName }
                        if (distributor != null) {
                            println("Ingrese el nombre del producto a actualizar: ")
                            val productToUpdate = scanner.nextLine()
                            val products = distributor.readProducts()
                            val product = products.find { it.name == productToUpdate }
                            if (product != null) {
                                print("Ingrese el nuevo precio del producto: ")
                                val newPrice = scanner.nextDouble()
                                val updatedProduct = Product(
                                    product.id,
                                    product.name,
                                    newPrice,
                                    product.stock,
                                    product.isAvailable
                                )
                                distributor.updateProduct(productToUpdate, updatedProduct)
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
                    4 -> {
                        scanner.nextLine()
                        println("Ingrese el nombre del distribuidor del que desea eliminar el producto: ")
                        val distributorName = scanner.nextLine()
                        val distributor = distributorController.readDistributors()
                            .find { it.name == distributorName }
                        if (distributor != null) {
                            println("Ingrese el nombre del producto a eliminar: ")
                            val productToUpdate = scanner.nextLine()
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
                    5 -> {
                        println("Saliendo de la aplicación!")
                        return
                    }
                    else -> {
                        println("Opción incorrecta, ingrese un número válido")
                        waiting()
                    }
                }
            }
            3 -> {
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

