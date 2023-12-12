package com.example.b2023gr2sw

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Kevin","k@k.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Joel","j@j.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3,"Maria","m@m.com")
                )
        }
    }
}