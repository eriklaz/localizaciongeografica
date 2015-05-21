package com.lazaro.localizaciongeografica;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;




public class MainActivity extends Activity {


    // se declaran//
            private Button btnActualizar, btnDesactivar;
            private TextView lblLatitud, lblLongitud, lblPresicion, lblEstadoProveedor;

            /* SE AGREGA DOS VARIABLES PARA EL LOCALIZADOR */
            private LocationManager locManager;
            private LocationListener locListener;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);


                // SE LIGAN//
                btnActualizar = (Button) findViewById(R.id.BtnActualizar);
                btnDesactivar = (Button) findViewById(R.id.BtnDesactivar);
                lblLatitud =(TextView) findViewById(R.id.LblPosLatitud);
                lblLongitud =(TextView) findViewById(R.id.LblPosLongitud);
                lblPresicion =(TextView) findViewById(R.id.LblPosPrecision);
                lblEstadoProveedor =(TextView) findViewById(R.id.LblEstado);

                btnActualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        comenzarLocalizacion();

                    }
                });
                btnDesactivar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        locManager.removeUpdates(locListener);
                    }
                });
            }
    private void  mostrarPosicion (Location loc) {
        if (loc != null) {
            lblLatitud.setText("Latirud:" + String.valueOf(loc.getLatitude()));
            lblLongitud.setText("Longitud:" + String.valueOf(loc.getLongitude()));
            lblPresicion.setText("Precision:" + String.valueOf(loc.getAccuracy()));
        } else {
            lblLatitud.setText("Latitud:(sin_datos)");
            lblLongitud.setText("Longitud:(sin_datos)");
            lblPresicion.setText("Precision:(sin_datos)");
        }
    }

    // COMIENZA LA LOCALIZACION
    private void comenzarLocalizacion() {
        //Obtenemos una referencia al LocationManager
        locManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ///Obtenemos la ultima posicion conocidos
        Location loc =
                locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //mostrar la ultima posicion conocida
        mostrarPosicion(loc);

        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("", "Provider Status:" + status);
                lblEstadoProveedor.setText("Provider Status:" + status);
            }

            @Override
            public void onProviderEnabled(String provider) {
                lblEstadoProveedor.setText("Provider ON");

            }

            @Override
            public void onProviderDisabled(String provider) {
                lblEstadoProveedor.setText("Provider OF");
            }
        };
        locManager.requestLocationUpdates(locManager.GPS_PROVIDER, 30000, 0, locListener);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}