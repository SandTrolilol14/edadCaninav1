# 🐶 EdadCanina · v1

Aplicación Android (Java) que calcula la edad "humana" de un perro multiplicando su edad por 7.

Esta es la **versión 1**: funciona, pero no comprueba lo que escribe el usuario. Tu tarea es convertirla en la **versión 2** (rama `v2-validacion`), que valida la entrada y controla los errores sin que la app se cierre.

---

## Objetivos

Al terminar sabrás:

- Detectar por qué una app se cierra (`NumberFormatException`) y evitarlo con `try/catch`.
- Validar datos de entrada: campo vacío, texto no numérico y rango de valores.
- Mostrar errores al usuario con `EditText.setError()`.
- Sustituir "números mágicos" por constantes.
- Llevar los textos a `strings.xml` y usar formatos con varios parámetros.
- Trabajar en una rama de Git distinta de `main`.

---

## Paso 0 · Prepara el proyecto

1. Haz un **fork** de este repositorio (botón *Fork*) y clona tu fork:
   ```bash
   git clone https://github.com/TU_USUARIO/edadCaninav1.git
   ```
2. Ábrelo en Android Studio y ejecútalo en el emulador.

## Paso 1 · Rompe la app (a propósito)

Antes de arreglar nada, comprueba qué falla. Prueba estas entradas y anota qué pasa:

| Entrada | ¿Qué ocurre? |
|---|---|
| *(campo vacío)* | |
| `abc` | |
| `3.5` | |
| `0` | |
| `-4` | |
| `99999999999` | |
| `400000000` | |

> 💡 Cuando la app se cierre, abre el **Logcat** y busca la excepción. ¿Qué método la lanza? ¿Por qué el último caso no cierra la app pero da un resultado absurdo?

## Paso 2 · Crea la rama de trabajo

```bash
git checkout -b v2-validacion
```

Todo lo que hagas a partir de aquí irá en esta rama; `main` se queda como está.

## Paso 3 · Corrige los fallos que ya trae la v1

Antes de añadir nada nuevo, limpia lo que hay:

1. **`activity_main.xml`**: busca la etiqueta de cierre `</TextView>>` y quita el `>` que sobra.
2. **`strings.xml`**: en `tu_edad_format` hay una falta de ortografía ("fuero"). Corrígela.
3. **Texto escrito "a fuego"**: el `android:hint="Ejemplo: 6"` del `EditText` está directamente en el layout. Llévalo a `strings.xml` con el nombre `hint_edad` y úsalo con `@string/hint_edad`.
   > 💡 Android Studio marca este aviso en amarillo (*Hardcoded string*). Prueba `Alt + Enter` encima.
4. **`MainActivity.java`**: hay tres `import` que no se usan (`Insets`, `ViewCompat`, `WindowInsetsCompat`). Elimínalos (`Ctrl + Alt + O`).

## Paso 4 · Añade los mensajes de error

En `res/values/strings.xml` añade tres textos nuevos:

```xml
<string name="error_vacio">Introduce la edad de tu perro</string>
<string name="error_no_numero">Introduce un número entero válido</string>
<string name="error_rango">La edad debe estar entre %1$d y %2$d años</string>
```

> ❓ ¿Qué significan `%1$d` y `%2$d`? ¿En qué orden se rellenan?

## Paso 5 · Restringe la entrada desde el layout

En el `EditText` (`@+id/age_input`) añade:

```xml
android:inputType="number"
android:maxLength="2"
android:gravity="center"
android:importantForAutofill="no"
```

| Atributo | Para qué sirve |
|---|---|
| `inputType="number"` | Muestra el teclado numérico: sin letras, sin signo menos ni decimales. |
| `maxLength="2"` | No deja escribir más de 2 cifras. |
| `gravity="center"` | Centra el número en el campo. |
| `importantForAutofill="no"` | Evita que el sistema ofrezca autocompletar. |

> ⚠️ Esto **no sustituye** a la validación en Java: el campo puede quedarse vacío y nunca conviene fiarse solo de la interfaz. Por eso seguimos con el código.

## Paso 6 · Sustituye los números mágicos por constantes

Al principio de la clase `MainActivity` declara:

```java
private static final int FACTOR_CANINO = 7;
private static final int EDAD_MINIMA = 1;
private static final int EDAD_MAXIMA = 30; // ningún perro supera esta edad
```

y usa `FACTOR_CANINO` en lugar del `7`.

## Paso 7 · Convierte las vistas en atributos de la clase

Ahora `ageEdit` y `resultText` son variables locales de `onCreate()`. Como vas a usarlas desde otros métodos, decláralas como atributos:

```java
private EditText ageEdit;
private TextView resultText;
```

y en `onCreate()` asígnalas **sin volver a escribir el tipo**:

```java
ageEdit = findViewById(R.id.age_input);
resultText = findViewById(R.id.result_text);
```

> ❓ ¿Qué pasaría si dejaras `EditText ageEdit = findViewById(...)` dentro de `onCreate()`?

## Paso 8 · Saca la lógica del botón a un método

El `setOnClickListener` debe quedar así de corto:

```java
button1.setOnClickListener(view -> calcularEdad());
```

Toda la lógica pasa a un método nuevo: `private void calcularEdad()`.

## Paso 9 · Crea un método para mostrar errores

```java
private void mostrarError(String mensaje) {
    // 1. Mostrar el mensaje en el EditText (setError)
    // 2. Poner el foco en el EditText (requestFocus)
    // 3. Borrar el resultado anterior del TextView
}
```

## Paso 10 · Valida la entrada en `calcularEdad()`

Implementa el método siguiendo este orden. En cuanto algo falle, llama a `mostrarError(...)` y sal con `return`:

1. **Leer** el texto del `EditText` quitando espacios (`trim()`).
2. **¿Está vacío?** → `error_vacio`. Recuerda que `Integer.parseInt("")` lanza `NumberFormatException`.
3. **Convertir a `int`** dentro de un `try/catch (NumberFormatException e)` → si falla, `error_no_numero`.
4. **¿Está en rango?** Si `age < EDAD_MINIMA || age > EDAD_MAXIMA` → `error_rango`. Para rellenar `%1$d` y `%2$d`:
   ```java
   getString(R.string.error_rango, EDAD_MINIMA, EDAD_MAXIMA)
   ```
5. **Todo correcto**: quita el error del campo (`setError(null)`), calcula `age * FACTOR_CANINO` y muestra el resultado con `tu_edad_format`.

> ❓ ¿Por qué limitar la edad a 30 evita también el desbordamiento que viste en el Paso 1?

## Paso 11 · Vuelve a probar

Comprueba que **la app nunca se cierra**:

| Entrada | Resultado esperado |
|---|---|
| *(vacío)* | Error: "Introduce la edad de tu perro" |
| `0` | Error: "La edad debe estar entre 1 y 30 años" |
| `31` | Error de rango |
| `1` | "Si tu perro fuera humano tendría 7 años" |
| `30` | "Si tu perro fuera humano tendría 210 años" |

> 💡 Con el teclado numérico no puedes escribir `abc` ni `-4`. ¿Cómo comprobarías que el `try/catch` funciona? (Pista: quita `inputType` un momento.)

## Paso 12 · Guarda y sube tu trabajo

```bash
git add .
git commit -m "Validación de la entrada y control de errores aritméticos"
git push -u origin v2-validacion
```

Comprueba en GitHub que tu fork tiene ahora dos ramas: `main` y `v2-validacion`.

---

## ✅ Lista de comprobación

- [ ] La app no se cierra con ninguna entrada.
- [ ] Todos los textos están en `strings.xml` (ningún aviso *Hardcoded string*).
- [ ] No quedan números mágicos en `MainActivity`.
- [ ] `onCreate()` solo prepara la pantalla; la lógica está en `calcularEdad()`.
- [ ] El trabajo está en la rama `v2-validacion`, no en `main`.

---

## EdadCaninaMVC

Versión 3 de la app (rama `v3-mvc`): hace lo mismo que la v2, pero organizada con **DataBinding** y el patrón **MVC**:

- **Modelo** (`modelo`): `EdadCaninaModelo` y el enum `Validacion`. Guarda el texto, la edad humana y la validación, valida, calcula y avisa cuando algo cambia (`BaseObservable`). No importa nada de `android.*`.
- **Vista** (`activity_main.xml` + `vista/AdaptadoresVista`): se pinta sola a partir del modelo con `@{}` y `@={}`, y muestra los errores con el atributo propio `app:errorValidacion`.
- **Controlador** (`controlador/EdadCaninaControlador`): recibe los clics de Calcular y Limpiar desde el XML y da órdenes al modelo.
- **MainActivity**: solo crea las piezas, las conecta y guarda el estado al girar.

Cada paso tiene su commit y su etiqueta (`paso-1` … `paso-7`). Hay 14 pruebas JUnit en `app/src/test`.

### Reto elegido: Teclado

Al pulsar la tecla «Hecho» del teclado se calcula la edad, igual que con el botón Calcular.

Cómo lo he resuelto:

1. En el `EditText` he puesto `android:imeOptions="actionDone"` para que la tecla de abajo a la derecha del teclado sea «Hecho».
2. En `AdaptadoresVista` he creado un segundo `@BindingAdapter`, `app:alPulsarHecho`, que recibe una acción (una interfaz `AccionTeclado` con un solo método, para poder escribirla como lambda en el XML).
3. El adaptador pone un `OnEditorActionListener` al campo: si la acción es `IME_ACTION_DONE`, ejecuta la acción. Devuelve `false` para que Android también cierre el teclado.
4. En el XML lo enlazo con el controlador: `app:alPulsarHecho="@{() -> controlador.calcular()}"`.

Así la tecla «Hecho» hace lo mismo que el botón, y la `MainActivity` sigue sin tener ningún listener.

### Explícalo con tus palabras

**1. ¿Qué diferencia hay entre `@{...}` y `@={...}`?**

`@{...}` es un enlace en un solo sentido, del modelo a la vista: cuando el modelo avisa de un cambio, la vista se actualiza, pero lo que haga el usuario no vuelve al modelo. Lo uso en el resultado (texto y visibilidad) y en el error.

`@={...}` es un enlace en los dos sentidos: además de lo anterior, cuando el usuario cambia la vista se llama al setter del modelo. Lo uso en el `EditText`: cada cifra que escribo llama a `setEdadTexto()`, así que el modelo siempre tiene el texto y no hace falta leerlo con `getText()`. Por eso el setter comprueba si el texto ha cambiado antes de avisar; si no, la vista y el modelo se llamarían el uno al otro sin parar.

**2. ¿Qué ocurre, paso a paso, desde que pulsas Calcular hasta que aparece «42»?**

1. Al escribir `6`, el enlace `@={modelo.edadTexto}` ya ha llamado a `modelo.setEdadTexto("6")`.
2. Pulso Calcular. El `android:onClick="@{() -> controlador.calcular()}"` llama al controlador.
3. El controlador llama a `modelo.calcular()`.
4. El modelo quita espacios, comprueba que no está vacío, lo convierte con `Integer.parseInt` (6) y comprueba que está entre 1 y 30.
5. Calcula `6 * FACTOR_CANINO = 42` y llama a `setEdadHumana(42)` y `setValidacion(OK)`. Cada setter hace `notifyPropertyChanged(BR.edadHumana)` y `notifyPropertyChanged(BR.validacion)`.
6. La clase generada `ActivityMainBinding` está escuchando al modelo. Al recibir el aviso, vuelve a evaluar las expresiones que usan esas propiedades:
   - el texto del resultado: `@string/tu_edad_format(modelo.edadHumana)` → «Si tu perro fuera humano tendría 42 años»;
   - la visibilidad: como la validación es `OK`, pasa a `VISIBLE`;
   - `app:errorValidacion` recibe `OK` y quita el error del campo.
7. En pantalla aparece «42». La `MainActivity` no ha hecho nada en todo el proceso.

**3. ¿Qué cambiarías para que el factor fuera 6 en vez de 7, y por qué solo tocas un fichero?**

Solo cambiaría `FACTOR_CANINO = 7` por `FACTOR_CANINO = 6` en `EdadCaninaModelo.java`.

Solo hay que tocar ese fichero porque el factor es una regla de negocio y todas las reglas están en el modelo, en una constante. La vista solo muestra `edadHumana` (no sabe cómo se calcula), el controlador solo llama a `calcular()` y la `MainActivity` solo conecta piezas. Ninguna de ellas usa el 7.

Las pruebas JUnit que esperan 42, 7 o 210 fallarían, y habría que actualizarlas con los valores nuevos. Eso es bueno: las pruebas avisan de que ha cambiado una regla.
