package com.example.examen01

import com.example.examen01.model.Distributor
import com.example.examen01.model.Product

class MemoryDatabase {
    companion object{
        val distributorsArrayList = arrayListOf<Distributor>()
        val productsListAC: ArrayList<Product> = arrayListOf()
        val productsListCN: ArrayList<Product> = arrayListOf()
        val productsListP: ArrayList<Product> = arrayListOf()

        init {

            productsListAC.add(Product(1,"Coca-cola",2.50,50,true))
            productsListAC.add(Product(2,"Yogurt Toni Mora",2.90,45,true))
            productsListCN.add(Product(2,"Poni Malta",0.35,0,false))
            productsListCN.add(Product(2,"Pilsener",1.50,100,true))
            productsListP.add(Product(2,"Doritos",1.00,70,true))

            distributorsArrayList.add(Distributor("Arca Continental", "Quito, Ecuador", "4012200", "arca@continental.org", productsListAC))
            distributorsArrayList.add(Distributor("Cervecería Nacional", "Guayaquil, Ecuador", "1800001246", "cerveceria@nacional.com", productsListCN))
            distributorsArrayList.add(Distributor("Pepsico", "Guayaquil, Ecuador", "2005150", "pepsico@hotmail.com", productsListP))
        }

        fun getProductsForDistributorByName(distributorName: String): List<Product> {
            val distributor = distributorsArrayList.find { it.name == distributorName }
            return distributor?.productList?: emptyList()
        }
    }
}