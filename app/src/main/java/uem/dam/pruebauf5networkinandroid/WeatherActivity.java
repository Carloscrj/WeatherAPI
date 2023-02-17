package uem.dam.pruebauf5networkinandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Retrofit;
import uem.dam.pruebauf5networkinandroid.data.WeatherRes;
import uem.dam.pruebauf5networkinandroid.javabeans.Coordenadas;
import uem.dam.pruebauf5networkinandroid.utils.ApiRestService;
import uem.dam.pruebauf5networkinandroid.utils.RetrofitClient;

public class WeatherActivity extends AppCompatActivity {

    public static String CLAVE_IDIOMA = "es";
    public static String CLAVE_KEY = "11ce4328111023379e0fdc9d28c24a02";

    public static String CLAVE_EXCLUDE = "minutely,hourly,daily,alerts,flags";

    TextView tvCiudad;
    ImageView ivIcono;

    TextView tvHora;
    TextView tvTemperatura;
    TextView tvHumedad;
    TextView tvLLuvia;
    TextView tvPrediccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tvCiudad = findViewById(R.id.tvCiudad);
        ivIcono = findViewById(R.id.ivIcono);
        tvHora = findViewById(R.id.tvHora);
        tvTemperatura = findViewById(R.id.tvTemperatura);
        tvHumedad = findViewById(R.id.tvHumedad);
        tvLLuvia = findViewById(R.id.tvLluvia);
        tvPrediccion = findViewById(R.id.tvPrediccion);

        Coordenadas coordenadas = getIntent().getParcelableExtra(MainActivity.CLAVE_COORDENADAS);

         if (isNetworkAvailable()){
            consumirWS(coordenadas.getLatitud(), coordenadas.getLongitud());
        } else {
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show();
        }

        
    }

    private void consumirWS(double latitud, double longitud) {
        Retrofit r = RetrofitClient.getClient(ApiRestService.BASE_URL);
        ApiRestService ars = r.create(ApiRestService.class);
        Call<WeatherRes> call = ars.obtenerClima(CLAVE_KEY, latitud, longitud, CLAVE_EXCLUDE, CLAVE_IDIOMA);
        call.enqueue(new retrofit2.Callback<WeatherRes>() {
            @Override
            public void onResponse(Call<WeatherRes> call, retrofit2.Response<WeatherRes> response) {

                if (response.isSuccessful()) {
                    WeatherRes weatherRes = response.body();
                    tvCiudad.setText(weatherRes.getTimezone());
                    // Obtenemos la imagen que queremos mostrar en el ImageView.
                    Drawable icono = getResources().getDrawable(weatherRes.getCurrently().getIconId(), getApplicationContext().getTheme());
                    ivIcono.setImageDrawable(icono);
                    int hora = weatherRes.getCurrently().getTime();
                    // Creamos un objeto Date con la hora que nos da la API en milisegundos para despues convertirlo a formato hora y minutos.
                    Date date = new Date(hora * 1000);
                    // Lo mostramos en el TextView pero que solo muestre la hora y los minutos.
                    tvHora.setText(date.getHours() + ":" + date.getMinutes());
                    // Convertimos la temperatura de Farenheit (suministrado por la API) a Celsius.
                    Double temperatura = weatherRes.getCurrently().getTemperature();
                    temperatura = (temperatura - 32) * 5 / 9;
                    int temperaturaEntera = temperatura.intValue();
                    tvTemperatura.setText(temperaturaEntera + "ºC");
                    tvHumedad.setText((weatherRes.getCurrently().getHumidity()*100) + "%");
                    tvLLuvia.setText((weatherRes.getCurrently().getPrecipProbability()*100) + "%");
                    tvPrediccion.setText(weatherRes.getCurrently().getSummary());
                } else {
                    Log.d("ERROR", response.errorBody().toString());
                }

            }



            @Override
            public void onFailure(Call<WeatherRes> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });

    }

     private boolean isNetworkAvailable() {
        boolean isAvailable=false;
        //Gestor de conectividad
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(WeatherActivity.CONNECTIVITY_SERVICE);

        //Objeto que recupera la información de la red
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //Si la información de red no es nula y estamos conectados
        //la red está disponible
        if(networkInfo!=null && networkInfo.isConnected()){
            isAvailable=true;
        }
        return isAvailable;
    }


}