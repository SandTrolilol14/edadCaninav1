package es.davante.edadcanina;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import es.davante.edadcanina.controlador.EdadCaninaControlador;
import es.davante.edadcanina.databinding.ActivityMainBinding;
import es.davante.edadcanina.modelo.EdadCaninaModelo;

public class MainActivity extends AppCompatActivity {

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

        // Entregamos las dos piezas a la vista: un setter por cada <variable> del XML
        binding.setModelo(modelo);
        binding.setControlador(controlador);
    }
}