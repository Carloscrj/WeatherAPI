package uem.dam.pruebauf5networkinandroid.javabeans;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Coordenadas implements Parcelable {

    private double latitud;
    private double longitud;

    public Coordenadas(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    protected Coordenadas(Parcel in) {
        latitud = in.readDouble();
        longitud = in.readDouble();
    }


    public static final Creator<Coordenadas> CREATOR = new Creator<Coordenadas>() {
        @Override
        public Coordenadas createFromParcel(Parcel in) {
            return new Coordenadas(in);
        }

        @Override
        public Coordenadas[] newArray(int size) {
            return new Coordenadas[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
