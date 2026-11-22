package es.davante.edadcanina.vista;

import android.content.Context;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

import es.davante.edadcanina.R;
import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;

public class AdaptadoresVista {

    // Clase de utilidades: solo métodos static, no tiene sentido crear objetos de ella
    private AdaptadoresVista() {
    }

    @BindingAdapter("errorValidacion")
    public static void setErrorValidacion(EditText campo, Validacion validacion) {
        // El Context para getString lo sacamos de la propia vista
        Context contexto = campo.getContext();

        // Traducimos la Validacion del modelo a un mensaje de strings.xml.
        // Aquí es donde viven los textos, no en el modelo
        String mensaje = null;
        if (validacion != null) {
            switch (validacion) {
                case VACIO:
                    mensaje = contexto.getString(R.string.error_vacio);
                    break;
                case NO_NUMERO:
                    mensaje = contexto.getString(R.string.error_no_numero);
                    break;
                case FUERA_DE_RANGO:
                    mensaje = contexto.getString(R.string.error_rango,
                            EdadCaninaModelo.EDAD_MINIMA, EdadCaninaModelo.EDAD_MAXIMA);
                    break;
                default:
                    // OK y SIN_CALCULAR no son errores: mensaje se queda en null
                    break;
            }
        }

        // setError(null) quita el error. Así, al escribir (validación SIN_CALCULAR)
        // el error desaparece solo, sin pulsar nada
        campo.setError(mensaje);

        // Si hay error, ponemos el foco en el campo para que el usuario lo corrija
        if (mensaje != null) {
            campo.requestFocus();
        }
    }
}