package es.davante.edadcanina.modelo;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import es.davante.edadcanina.BR;

public class EdadCaninaModelo extends BaseObservable {

    // Las constantes son reglas de negocio, por eso viven en el modelo.
    // Son public para que la vista pueda usarlas al montar el mensaje de rango.
    public static final int FACTOR_CANINO = 7;
    public static final int EDAD_MINIMA = 1;
    public static final int EDAD_MAXIMA = 30; // ningún perro supera esta edad

    // Estado del modelo: lo escrito, el resultado y cómo fue la validación
    private String edadTexto = "";
    private int edadHumana = 0;
    private Validacion validacion = Validacion.SIN_CALCULAR;

    // @Bindable hace que DataBinding cree BR.edadTexto, BR.edadHumana y BR.validacion,
    // que son los identificadores que usamos para avisar de qué ha cambiado
    @Bindable
    public String getEdadTexto() {
        return edadTexto;
    }

    @Bindable
    public int getEdadHumana() {
        return edadHumana;
    }

    @Bindable
    public Validacion getValidacion() {
        return validacion;
    }

    public void setEdadTexto(String edadTexto) {
        // Nunca guardamos null para no tener que comprobarlo en cada metodo
        if (edadTexto == null) {
            edadTexto = "";
        }
        // Si el texto no ha cambiado NO avisamos. Con el enlace bidireccional (@=)
        // la vista escribe en el modelo y el modelo avisa a la vista: sin este if
        // se volverían a llamar el uno al otro sin parar
        if (edadTexto.equals(this.edadTexto)) {
            return;
        }
        this.edadTexto = edadTexto;
        notifyPropertyChanged(BR.edadTexto);

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

    // Setters privados: solo calcular() y setEdadTexto() pueden cambiar el resultado.
    // Solo avisamos si el valor cambia de verdad, para no repintar la vista para nada
    private void setEdadHumana(int edadHumana) {
        if (this.edadHumana == edadHumana) {
            return;
        }
        this.edadHumana = edadHumana;
        notifyPropertyChanged(BR.edadHumana);
    }

    private void setValidacion(Validacion validacion) {
        if (this.validacion == validacion) {
            return;
        }
        this.validacion = validacion;
        notifyPropertyChanged(BR.validacion);
    }
}