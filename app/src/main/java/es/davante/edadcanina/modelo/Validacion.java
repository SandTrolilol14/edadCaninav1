package es.davante.edadcanina.modelo;

public enum Validacion {

    SIN_CALCULAR,    // todavía no se ha pulsado Calcular (o el texto ha cambiado después)
    OK,              // edad válida: hay resultado que mostrar
    VACIO,           // el campo está vacío
    NO_NUMERO,       // lo escrito no es un número entero
    FUERA_DE_RANGO   // número fuera de EDAD_MINIMA..EDAD_MAXIMA

}
