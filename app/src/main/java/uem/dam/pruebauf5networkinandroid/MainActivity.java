package uem.dam.pruebauf5networkinandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uem.dam.pruebauf5networkinandroid.javabeans.Coordenadas;

public class MainActivity extends AppCompatActivity {

    public static final String CLAVE_COORDENADAS = "COORDENADAS";
    EditText etLatitud;
    EditText etLongitud;
    Button btnConsTiempo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLatitud = findViewById(R.id.etLatitud);
        etLongitud = findViewById(R.id.etLongitud);
        btnConsTiempo = findViewById(R.id.btnConsTiempo);

        btnConsTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String latitud = etLatitud.getText().toString();
                String longitud = etLongitud.getText().toString();

                if (latitud.isEmpty() || longitud.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Campos vacios.Introduce los datos", Toast.LENGTH_SHORT).show();
                }else{
                    //Coge la info del editext y métela en un objeto:
                    Coordenadas coordenadas = new Coordenadas(Double.valueOf(latitud), Double.valueOf(longitud));
                    //...y pásala al siguiente activity:
                    Intent i = new Intent(MainActivity.this, WeatherActivity.class);

                    i.putExtra(CLAVE_COORDENADAS, coordenadas);

                    startActivity(i);
                }

            }
        });
    }
}