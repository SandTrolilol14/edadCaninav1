package es.davante.edadcanina;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import es.davante.edadcanina.databinding.ActivityMainBinding;
import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;

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

        binding.btnCalc.setOnClickListener(view -> calcularEdad());
    }

    private void calcularEdad() {
        // Ya no leemos el EditText: gracias a @={...} el modelo ya tiene el texto.
        // Tampoco hacemos setText del resultado: el XML se repinta solo al avisar el modelo
        Validacion resultado = modelo.calcular();

        // El error todavía lo pintamos a mano (en el paso 5 lo pasamos al XML)
        switch (resultado) {
            case OK:
                binding.ageInput.setError(null);
                break;
            case VACIO:
                mostrarError(getString(R.string.error_vacio));
                break;
            case NO_NUMERO:
                mostrarError(getString(R.string.error_no_numero));
                break;
            case FUERA_DE_RANGO:
                mostrarError(getString(R.string.error_rango,
                        EdadCaninaModelo.EDAD_MINIMA, EdadCaninaModelo.EDAD_MAXIMA));
                break;
            default:
                break;
        }
    }

    private void mostrarError(String mensaje) {
        binding.ageInput.setError(mensaje);
        binding.ageInput.requestFocus();
    }
}