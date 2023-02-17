package uem.dam.pruebauf5networkinandroid.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import uem.dam.pruebauf5networkinandroid.data.WeatherRes;

public interface ApiRestService {

    public static final String BASE_URL = "https://api.darksky.net/forecast/";

    @GET("{key}/{latitud},{longitud}")
    Call<WeatherRes> obtenerClima(
            @Path("key")String key,
            @Path("latitud") double latitud,
            @Path("longitud")double longitud,
            @Query("exclude") String exclude, // aquí le estamos diciendo esto (?exclude=) no haria falta ponerlo en CLAVE_EXCLUDE en WeatherActivity
            @Query("lang") String lang); // aquí le estamos diciendo esto (&lang=) no haria falta ponerlo en CLAVE_EXCLUDE en WeatherActivity

}
