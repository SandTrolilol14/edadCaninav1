package es.davante.edadcanina.modelo;

public class EdadCaninaModelo {

    // Las constantes son reglas de negocio, por eso viven en el modelo.
    // Son public para que la vista pueda usarlas al montar el mensaje de rango.
    public static final int FACTOR_CANINO = 7;
    public static final int EDAD_MINIMA = 1;
    public static final int EDAD_MAXIMA = 30; // ningún perro supera esta edad

    // Estado del modelo: lo escrito, el resultado y cómo fue la validación
    private String edadTexto = "";
    private int edadHumana = 0;
    private Validacion validacion = Validacion.SIN_CALCULAR;

    public String getEdadTexto() {
        return edadTexto;
    }

    public int getEdadHumana() {
        return edadHumana;
    }

    public Validacion getValidacion() {
        return validacion;
    }

    public void setEdadTexto(String edadTexto) {
        // Nunca guardamos null para no tener que comprobarlo en cada método
        if (edadTexto == null) {
            edadTexto = "";
        }
        // Si el texto no ha cambiado no hacemos nada (será clave en el paso 4)
        if (edadTexto.equals(this.edadTexto)) {
            return;
        }
        this.edadTexto = edadTexto;
        // Regla de negocio: si el texto cambia, el resultado anterior ya no vale.
        // Va aquí, en el setter, porque es el único sitio por donde cambia el texto
        setEdadHumana(0);
        setValidacion(Validacion.SIN_CALCULAR);
    }

    public Validacion calcular() {
        String texto = edadTexto.trim();

        // Comprobamos vacío antes de parseInt, porque parseInt("") lanza excepción
        if (texto.isEmpty()) {
            return error(Validacion.VACIO);
        }

        int edad;
        try {
            edad = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            // Capturamos la excepción para que la app nunca se cierre con "abc" o "3.5"
            return error(Validacion.NO_NUMERO);
        }

        // Limitar el rango también evita el desbordamiento del int al multiplicar
        if (edad < EDAD_MINIMA || edad > EDAD_MAXIMA) {
            return error(Validacion.FUERA_DE_RANGO);
        }

        setEdadHumana(edad * FACTOR_CANINO);
        setValidacion(Validacion.OK);
        return Validacion.OK;
    }

    // Si hay error no puede quedar un resultado viejo: lo ponemos a 0
    private Validacion error(Validacion tipo) {
        setEdadHumana(0);
        setValidacion(tipo);
        return tipo;
    }

    // Setters privados: solo calcular() y setEdadTexto() pueden cambiar el resultado
    private void setEdadHumana(int edadHumana) {
        this.edadHumana = edadHumana;
    }

    private void setValidacion(Validacion validacion) {
        this.validacion = validacion;
    }
}