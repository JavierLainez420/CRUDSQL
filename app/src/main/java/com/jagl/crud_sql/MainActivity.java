package com.jagl.crud_sql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private EditText et_codigo, et_descripcion, et_precio;
    private Button btn_guardar, btn_consultar1, btn_consultar2, btn_eliminar, btn_actualizar, btnacercade, salir;
    private TextView tv_resultado;

    boolean inputEt = false;
    boolean inputEd = false;
    boolean input1 = false;
    int resultadoInsert = 0;


    Modal ventanas = new Modal();
    ConexionSQLite conexion = new ConexionSQLite(this);
    Dto datos = new Dto();
    AlertDialog.Builder dialogo;
    private Context context;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_close)
                    .setTitle("Warning")
                    .setMessage("¿Realmente desea salir?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finishAffinity();
                        }
                    })
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close));
        toolbar.setTitleTextColor(getResources().getColor(R.color.purple_200));
        toolbar.setTitleMargin(0,0,0,0);
        toolbar.setSubtitle("CRUD SQLite 2022");
        toolbar.setTitle("Javier Lainez");
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmacion();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanas.Search(MainActivity.this);
            }
        });


        et_codigo = findViewById(R.id.etCodigo);
        et_descripcion = findViewById(R.id.etDescripcion);

        et_codigo = findViewById(R.id.etCodigo);
        et_descripcion = findViewById(R.id.etDescripcion);
        et_precio = findViewById(R.id.etPrecio);
        btn_guardar = findViewById(R.id.btnGuardar);
        btn_consultar1 = findViewById(R.id.btnConsultaCod);
        btn_consultar2 = findViewById(R.id.btnConsultaDes);
        btn_eliminar = findViewById(R.id.btnGuardar);
        btn_actualizar = findViewById(R.id.btnEditar);

        String senal = "";
        String codigo = "";
        String descripcion = "";
        String precio = "";

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                codigo = bundle.getString("codigo");
                senal = bundle.getString("senal");
                descripcion = bundle.getString("descripcion");
                precio = bundle.getString("precio");
                if (senal.equals("1")){
                    et_codigo.setText(codigo);
                    et_descripcion.setText(descripcion);
                    et_precio.setText(precio);
                }
            }
        }catch (Exception e){

        }
    }

    private void confirmacion(){
        String mensaje = "¿Realmente desea salir?";
        dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setIcon(R.drawable.ic_close);
        dialogo.setTitle("Warning");
        dialogo.setMessage(mensaje);
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_limpiar){
            et_codigo.setText(null);
            et_descripcion.setText(null);
            et_precio.setText(null);
            return true;
        }else if (id == R.id.action_listaArticulos){
            Intent spinnerActivity = new Intent(this, ConsultSpinner.class);
            spinnerActivity.putExtra("valor", "nada");
            startActivity(spinnerActivity);
            return true;
        }else if(id == R.id.action_listaArticulos1){
            Intent listViewActivity = new Intent(MainActivity.this, ListViewArticulos.class);
            startActivity(listViewActivity);
            return true;
        }else if(id == R.id.action_recyclerview){
            Intent recyclerview = new Intent(MainActivity.this, consulta_recyclerView.class);
            startActivity(recyclerview);
            return true;
        }else if (id==R.id.action_acercade){
           Intent acerca = new  Intent(MainActivity.this,AcercaDe.class);
startActivity(acerca);
            return true;

}
        return super.onOptionsItemSelected(item);
    }

    public void guardar(View view) {
        if (et_codigo.getText().toString().length()==0){
            et_codigo.setError("Campo obligatorio");
            inputEt = false;
        }else {
            inputEt = true;
        }
        if (et_descripcion.getText().toString().length()==0){
            et_descripcion.setError("Campo obligatorio");
            inputEd = false;
        }else {
            inputEd = true;
        }
        if (et_precio.getText().toString().length()==0){
            et_precio.setError("Campo obligatorio");
            input1 = false;
        }else {
            input1 = true;
        }
        if (inputEt && inputEd && input1){
            try {
                datos.setCodigo(Integer.parseInt(et_codigo.getText().toString()));
                datos.setDescripcion(et_descripcion.getText().toString());
                datos.setPrecio(Double.parseDouble(et_precio.getText().toString()));

                if (conexion.InserTradicional(datos)){
                    Toast.makeText(this, "Registro agregado satisfactoriamente!", Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                }else {
                    Toast.makeText(this, "ERROR. Ya existe un registro\n" + "Código: "+ et_codigo.getText().toString(), Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                }
            }catch (Exception e){
                Toast.makeText(this, "ERROR. Ya existe", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void limpiarDatos() {
        et_codigo.setText(null);
        et_descripcion.setText(null);
        et_precio.setText(null);
        et_codigo.requestFocus();
    }

    public void consultaCod(View view) {
        if (et_codigo.getText().toString().length()==0){
            et_codigo.setError("Campo obligatorio");
            inputEt = false;
        }else {
            inputEt = true;
        }
        if (inputEt) {
            String codigo = et_codigo.getText().toString();
            datos.setCodigo(Integer.parseInt(codigo));
            if (conexion.consultaArticulos(datos)) {
                et_descripcion.setText(datos.getDescripcion());
                et_precio.setText("" + datos.getPrecio());
            } else {
                Toast.makeText(this, "No existe un articulo con dicho codigo", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }else {
            Toast.makeText(this, "Ingrese el codigo del articulo a buscar", Toast.LENGTH_SHORT).show();
        }

    }


    public void consultaDes(View view) {
        if (et_descripcion.getText().toString().length()==0){
            et_descripcion.setError("Campo obligatorio");
            inputEd = false;
        }else {
            inputEd = true;
        }
        if (inputEd){
            String descripcion = et_descripcion.getText().toString();
            datos.setDescripcion(descripcion);
            if (conexion.consultarDescripcion(datos)){
                et_codigo.setText("" + datos.getCodigo());
                et_descripcion.setText(datos.getDescripcion());
                et_precio.setText("" + datos.getPrecio());
            }else {
                Toast.makeText(this, "No existe un articulo con dicha descripcion", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }else{
            Toast.makeText(this, "Ingrese la descripcion del articulo a buscar", Toast.LENGTH_SHORT).show();
        }
    }

    public void borrar(View view) {
        if (et_codigo.getText().toString().length()==0){
            et_codigo.setError("Campo obligatorio");
            inputEt = false;
        }else   {
            inputEt = true;
        }if (inputEt){
            String cod = et_codigo.getText().toString();
            datos.setCodigo(Integer.parseInt(cod));
            if (conexion.bajaCodigo(MainActivity.this, datos)){
                limpiarDatos();
            }else   {
                Toast.makeText(this, "No existe un articulo con dicho codigo", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }
    }

    public void editar(View view) {
        if (et_codigo.getText().toString().length()==0){
            et_codigo.setError("campo obligatorio");
            inputEt = false;
        }else  {
            inputEt = true;
        }
        if (inputEt) {
            String cod = et_codigo.getText().toString();
            String descripcion = et_descripcion.getText().toString();
            double precio = Double.parseDouble(et_precio.getText().toString());

            datos.setCodigo(Integer.parseInt(cod));
            datos.setDescripcion(descripcion);
            datos.setPrecio(precio);
            if (conexion.modificar(datos)){
                Toast.makeText(this, "Registro Modificado Correctamente", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "No se han encontrado resultados para la busqueda", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void acercade(View view) {

}}