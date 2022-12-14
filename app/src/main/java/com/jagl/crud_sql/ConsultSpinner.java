package com.jagl.crud_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ConsultSpinner extends AppCompatActivity {
    private Spinner sp_options;
    private TextView tv_cod, tv_descripcion, tv_precio,tv_idcat;

    ConexionSQLite conexion = new ConexionSQLite(this);
    Dto datos = new Dto();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_spinner);

        sp_options = findViewById(R.id.opciones);
        tv_cod = findViewById(R.id.tv_cod);
        tv_descripcion = findViewById(R.id.tv_des);
        tv_precio = findViewById(R.id.tv_preciospi);
        tv_idcat = findViewById(R.id.tvidcat);

        conexion.consultaListaArticulos();

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, conexion.obtenerListaArticulos());
        sp_options.setAdapter(adaptador);

        try {




       sp_options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    tv_cod.setText("Codigo: "+conexion.consultaListaArticulos().get(i-1).getCodigo());
                    tv_descripcion.setText("Descripcion: "+conexion.consultaListaArticulos().get(i-1).getDescripcion());
                    tv_precio.setText("Precio: "+String.valueOf(conexion.consultaListaArticulos().get(i-1).getPrecio()));
                    tv_idcat.setText("Categoria: " + String.valueOf(conexion.consultaListaArticulos().get(i-1).getIdcategoria()));

                }else {
                    tv_cod.setText("Codigo: ");
                    tv_descripcion.setText("Descripcion: ");
                    tv_precio.setText("Precio: ");
                    tv_idcat.setText("Categoria: ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        }catch (Exception e){
            Log.e("Error",e.toString());
        }
    }
}