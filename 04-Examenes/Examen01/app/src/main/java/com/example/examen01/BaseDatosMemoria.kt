package com.example.examenib

import com.example.examen01.model.Distributor
import Model.Product

class BaseDatosMemoria {
    companion object{
        val distributorsArray = arrayListOf<Distributor>()
        val carsList: MutableList<Product> = mutableListOf()
        init {
            distributorsArray.add(Distributor("Arca Continental", "Quito, Ecuador", "4012200", "arca@continental.org", carsList))
            distributorsArray.add(Distributor("Cervecería Nacional", "Guayaquil, Ecuador", "1800001246", "cerveceria@nacional.com", carsList))
            distributorsArray.add(Distributor("Pepsico", "Guayaquil, Ecuador", "2005150", "pepsico@hotmail.com", carsList))
        }
    }
}