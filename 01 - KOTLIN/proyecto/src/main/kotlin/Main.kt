import java.util.*;

fun main(){
    println("Hello World!")
    //INMUTABLES (NO se reasigna "=")
    val inmutable: String = "Kevin";
    // inmutabe = "Condor";

    // Mutables (Re asignar)
    var mutable: String = "Kevin";
    mutable = "Condor"

    // val > var
    // Duck Typing
    var ejemploVariable = "Kevin Condor";
    val edadEjemplo:Int = 12;
    ejemploVariable.trim();
    // ejemploVariable = edadEjemplo;

    // Variable primitivo
    val nombreProfesor: String = "Adrian Eguez";
    val sueldo: Double = 1.2;
    val estadoCivil: Char = 'C';
    val mayorEdad: Booelan = true;
    // Clases Java
    val fechaNacimiento: Date = Date();

    //Switch
    val estadoCivilWhen = "C";
    when(estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        ("S") -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }
    val esSoltero = (estadoCivilWhen == "S");
    val coqueteo = if (esSoltero) "Si" else "No";

    calcularSueldo(10.00);
    calcularSueldo(10.00, 12.00, 20.00);
    calcularSueldo(10.00 bonoEspecial = 20.00); //Named parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00); // Parametros nombrados

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null,null)
    sumaUno.sumar();
    sumaDos.sumar();
    sumaTres.sumar();
    sumaCuatro.sumar();
    println(Suma.pi);
    println(Suma.elevarAlCuadrado(2));
    println(Suma.historialSumas);

    //Arreglo Estático
    val arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(arregloEstatico)

    //Arreglo Dinámico
    val arregloDinamico:ArrayList<Int> = arrayListOf<Int>(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10;
    )
    println(arregloDinamico);
    arregloDinamico.add(11);
    arregloDinamico.add(12);
    println(arregloDinamico);


    //FOR EACH -> Unit
    //Iterar un arreglo

    val respuestaForEach: Unit = arregloDinamico
            .forEach { valorActual: Int, ->
                println("Valor actual: ${valorActual}")
            }
    // it (en ingles eso) significa el elemento iterado
    arregloDinamico.forEach { println("Valor actual: ${it}")}

    arregloEstatico
            .forEachIndexed {indice: Int, valorActual: Int ->
                println("Valor ${valorActual} Indice: ${indice}")
            }


    // MAP -> Muta el arreglo (Cabia el  arreglo)
    // 1) Enviemos el nuevo valor de la iteración
    // 2) Nos devuelve es un NUEVO arreglo con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
            .map{ valorActual: Int ->
                return@map valorActual.toDouble() + 100.00
            }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }

    //Filter -> FILTRAR EL ARREGLO
    // 1) Devolver una expresión (TREU o FALSE)
    // 2) Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
            .filter{ valorActual: Int ->
                //Expresion condicion
                val mayoresACinco: Boolean = valorActual > 5
                return@filter mayoresACinco
            }
    val respuestaFilterDos = arregloDinamico.filter{ it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

}

abstract class NumerosJava{
    protected val numeroUno: Int;
    private val numeroDos: Int;
    constructor(
            uno: Int,
            dos: Int
    ){//Bloque de código del constructor
        this.numeroUno = uno;
        this.numeroDos = dos;
        println("Inicializando")
    }
}

abstract class Numeros(//Constructor PRIMARIO
        //Ejemplo:
        // uno: Int, (Parametro (sin modificador de acceso))
        // private var uno: Int, // Propiedad Publica Clase numeros.uno
        // var uno: Int, // Propiedad de la clase (por defecto es PUBLIC)
        // public var uno: Int,
        protected val numeroUno: Int; // Propiedad de la clase protected numero.numeroUno
private val numeroDos: Int; // Propiedad de la clase protected numeros.numeroDos
){
    // var cedula: string = "" (public es por defecto)
    // private valorCalculado: Int = 0 (private)
    init{ // bloque constructor primario
        this.numeroUno, this.numeroDos; // this es opcional
        numeroUno; numeroDos; // sin el "this", es lo mismo
        println("Inicializando")
    }
}

class Suma(//Constructor Primario Suma
        unoParametro: Int //Parametro
        dosParametro: Int, //Parametro
): Numeros(unoParametro, dosParametro){ //Extendiendo y mandando los parametros (super)
    init{// bloque de codigo constructor primario
        this.numeroUno;
        this.numeroDos;
    }
    constructor( //Segundo constructor
            uno: Int?, //Parametros
            dos: Int //Parametros
    ):this (
            if(uno == null) 0 else uno,
            dos
    )

    constructor( //Tercer constructor
            uno: Int, //Parametros
            dos: Int? //Parametros
    ):this(
            uno,
            if (dos == null) 0 else dos
    )

    constructor(//cuarto constructor
            uno: Int?, //parametros
            dos: Int? // parametros
    ): this(
            if(uno == null) 0 else uno,
            if(dos == null) 0 else dos
    )

    //public por defecto, también se puede usar private o protected
    fun sumar(): Int{
        val total = numeroUno + numeroDos:
        //Suma.agregarHistorial(total)
        agregarHistorial(total);
        return total;
    }

    //Atriibutos o métodos compartidos
    companion object{
        //entre las instancias
        val pi = 3.14;
        fun elevarAlCuadrado(num: Int): Int{
            return num * num;
        }
        val historialSumas = arrayListOf<Int>();
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma);
        }

    }

}


// void -> Unit
fun imprimirNombre(nombre: String): Unit{
    // "Nombre: " + nombre;
    println("Nombre: ${nombre}") //template strings
}

fun calcularSueldo(
        sueldo: Double, // Requerido
        tasa: Double = 12.00, // Opcional(defecto)
        bonoEspecial: Double? = null, // Opcion null -> nullable(puede tener el valor null)
): Double{
    //Int -> Int? (nullable)
    //String -> String? = (nullable)
    //Date -> Date? = (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa);
    }else{
        return sueldo * (100/tasa) + bonoEspecial;
    }
}






