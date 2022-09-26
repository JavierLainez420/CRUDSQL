package com.jagl.crud_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class ListViewArticulos extends AppCompatActivity {
    ListView listViewPersonas;
    ArrayAdapter adaptador;
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter adapter;
    String[] version = {"Aestro" , "Blender", "CupCake", "Donut", "Eclair",
            "Froyo","GingerBread","HoneyComb","IceCream Sandwich","Jelly Bean",
            "KitKat","Lollipop","Marshmallow","Nought", "Oreo"};

    ConexionSQLite conexion = new ConexionSQLite(this);
    Dto datos = new Dto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_articulos);

        listViewPersonas = findViewById(R.id.listViewPersonas);
        searchView = findViewById(R.id.searchView);

        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                conexion.consultaListaArticulos1());
        listViewPersonas.setAdapter(adaptador);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                String text = s;
                adaptador.getFilter().filter(text);
                return false;
            }
        });

        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String informacion = "Codigo: " + conexion.consultaListaArticulos().get(i).getDescripcion() + "\n";
                informacion += "Descripcion: " + conexion.consultaListaArticulos().get(i).getDescripcion() + "\n";
                informacion += "Precio: "+conexion.consultaListaArticulos().get(i).getPrecio();
                informacion += "ID Categoria: "+conexion.consultaListaArticulos().get(i).getIdcategoria();

                Dto articulos = conexion.consultaListaArticulos().get(i);
                Intent intent = new Intent(ListViewArticulos.this, DetallesArticulos.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("articulo", articulos);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}