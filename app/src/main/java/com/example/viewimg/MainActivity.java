package com.example.viewimg;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Bitmap> imagenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        imagenes = new ArrayList<>();

        // Realizar la solicitud HTTP para obtener las imágenes desde el servidor
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.40.1/examapp/viewimg.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            // Iterar sobre los objetos JSON en la respuesta
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String imagenBase64 = jsonObject.getString("imagen");

                                // Decodificar la imagen desde la cadena Base64
                                byte[] imagenBytes = Base64.decode(imagenBase64, Base64.DEFAULT);
                                Bitmap imagenBitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);

                                // Agregar la imagen al ArrayList
                                imagenes.add(imagenBitmap);
                            }

                            // Crear el adaptador personalizado y asignarlo al ListView
                            ImageListAdapter adapter = new ImageListAdapter(MainActivity.this, imagenes);
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        // Agregar la solicitud a la cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
