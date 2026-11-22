package es.davante.edadcanina;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import es.davante.edadcanina.databinding.ActivityMainBinding;
import es.davante.edadcanina.modelo.EdadCaninaModelo;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EdadCaninaModelo modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        modelo = new EdadCaninaModelo();

        // Entregamos el modelo a la vista. El método se llama setModelo
        // porque la <variable> del XML se llama "modelo"
        binding.setModelo(modelo);

        // Solo pedimos al modelo que calcule: el resultado (@{...}) y el error
        // (app:errorValidacion) los pinta el XML solo cuando el modelo avisa
        binding.btnCalc.setOnClickListener(view -> modelo.calcular());
    }
}