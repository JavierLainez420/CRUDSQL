package com.jagl.crud_sql;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class consulta_recyclerView extends AppCompatActivity {

    private RecyclerView recycler;
    private  AdaptadorArticulos adaptadorArticulos;
    ConexionSQLite datos = new ConexionSQLite(consulta_recyclerView.this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recycler = findViewById(R.id.rview);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

       // adaptadorArticulos = new AdaptadorArticulos(consulta_recyclerView.this,obtenerArticulos());
      //  adaptadorArticulos = new AdaptadorArticulos(consulta_recyclerView.this, datos.mostrarArticulos());
        recycler.setAdapter(adaptadorArticulos);
    }


    /*public List<Dto>obtenerArticulos(){
        List<Dto>articulos  = new ArrayList<>();
        articulos.add(new Dto(1, "Laptop", 200.99));
        articulos.add(new Dto(2, "Impresora HP", 352.5));
        articulos.add(new Dto(3, "Disco Duro", 25.5));
        articulos.add(new Dto(4, "OTra cosa", 50.6));
        return articulos;
    }*/



}
