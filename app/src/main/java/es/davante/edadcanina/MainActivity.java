package es.davante.edadcanina;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import es.davante.edadcanina.controlador.EdadCaninaControlador;
import es.davante.edadcanina.databinding.ActivityMainBinding;
import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;

public class MainActivity extends AppCompatActivity {

    // Claves del Bundle como constantes para no equivocarnos al escribirlas dos veces
    private static final String CLAVE_EDAD_TEXTO = "edad_texto";
    private static final String CLAVE_CALCULADO = "calculado";

    // Es atributo porque lo necesitamos también en onSaveInstanceState
    private EdadCaninaModelo modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Vista: inflamos el layout y obtenemos el binding
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Modelo y controlador. El controlador recibe el modelo para poder darle órdenes
        modelo = new EdadCaninaModelo();
        EdadCaninaControlador controlador = new EdadCaninaControlador(modelo);

        if (savedInstanceState != null) {
            modelo.setEdadTexto(savedInstanceState.getString(CLAVE_EDAD_TEXTO, ""));
            // No guardamos el resultado: calcular() da siempre lo mismo para el
            // mismo texto, así que basta con volver a calcular (y vuelve el error si lo había)
            if (savedInstanceState.getBoolean(CLAVE_CALCULADO, false)) {
                modelo.calcular();
            }
        }

        // Entregamos las dos piezas a la vista: un setter por cada <variable> del XML
        binding.setModelo(modelo);
        binding.setControlador(controlador);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CLAVE_EDAD_TEXTO, modelo.getEdadTexto());
        outState.putBoolean(CLAVE_CALCULADO, modelo.getValidacion() != Validacion.SIN_CALCULAR);
    }
}