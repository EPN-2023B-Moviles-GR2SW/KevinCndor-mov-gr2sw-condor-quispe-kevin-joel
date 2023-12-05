package Model

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

    fun readProducts(): List<Product>{
        return productsList.toList()
    }

    fun updateProduct(name: String, updatedProduct: Product){
        val indexToUpdate = mutableListOf<Int>()

        // Encuentra los índices de los elementos que cumplen la condición
        for (index in productsList.indices) {
            val product = productsList[index]
            if (product.name == name) {
                indexToUpdate.add(index)
            }
        }

        // Actualiza los elementos de la lista según los índices encontrados
        for (index in indexToUpdate) {
            productsList[index] = updatedProduct
        }
    }

    fun deleteProduct(name: String){
        val productsToDelete = mutableListOf<Product>()

        productsList.forEach { product ->
            if(product.name == name){
                productsToDelete.add(product)
            }
        }

        productsList.removeAll(productsToDelete)
    }

}