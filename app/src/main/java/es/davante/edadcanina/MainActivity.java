package es.davante.edadcanina;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import es.davante.edadcanina.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Constantes: evitamos números mágicos y, si cambian, se cambian en un solo sitio
    private static final int FACTOR_CANINO = 7;
    private static final int EDAD_MINIMA = 1;
    private static final int EDAD_MAXIMA = 30;

    // El binding sustituye a todos los findViewById: tiene un campo por cada vista con id.
    // Es un atributo porque lo usamos también desde calcularEdad() y mostrarError()
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // DataBindingUtil infla el layout igual que setContentView, pero además
        // nos devuelve el objeto binding ya enlazado con las vistas
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // btn_calc pasa a ser binding.btnCalc (snake_case → camelCase)
        binding.btnCalc.setOnClickListener(view -> calcularEdad());
    }

    private void calcularEdad() {
        // 1. Leer el texto quitando espacios
        String ageString = binding.ageInput.getText().toString().trim();

        // 2. ¿Está vacío? parseInt("") lanzaría NumberFormatException
        if (ageString.isEmpty()) {
            mostrarError(getString(R.string.error_vacio));
            return;
        }

        // 3. Convertir a int controlando la excepción para que la app no se cierre
        int age;
        try {
            age = Integer.parseInt(ageString);
        } catch (NumberFormatException e) {
            mostrarError(getString(R.string.error_no_numero));
            return;
        }

        // 4. ¿Está en rango? Limitar a 30 también evita desbordar el int al multiplicar
        if (age < EDAD_MINIMA || age > EDAD_MAXIMA) {
            mostrarError(getString(R.string.error_rango, EDAD_MINIMA, EDAD_MAXIMA));
            return;
        }

        // 5. Todo correcto: quitamos el error y mostramos el resultado
        binding.ageInput.setError(null);
        int dogAge = age * FACTOR_CANINO;
        binding.resultText.setText(getString(R.string.tu_edad_format, dogAge));
    }

    private void mostrarError(String mensaje) {
        binding.ageInput.setError(mensaje);
        binding.ageInput.requestFocus();
        binding.resultText.setText("");
    }
}