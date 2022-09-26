package com.jagl.crud_sql;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConexionSQLite extends SQLiteOpenHelper {

    boolean estadoDelete = true;
    ArrayList<String> listaArticulos;
    ArrayList<Dto> articulosList;


    public ConexionSQLite(@Nullable Context context) {
        super(context, "administracion.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table tb_categorias (idcategoria integer(11) not null primary key, nombrecategoria varchar(50) not null, estadocategoria integer(11) not null, fecharegistro datetime not null)");
        sqLiteDatabase.execSQL("create table tb_productos (codigo integer(11) not null primary key, descripcion varchar(100) not null, precio real not null, idcategoria integer(11) not null, FOREIGN KEY(idcategoria) REFERENCES tb_categorias(idcategoria))");

        sqLiteDatabase.execSQL("insert into tb_categorias values(1, 'Smartphone', 1, datetime('now','localtime')), (2, 'Tablets', 1, datetime('now','localtime')), (3, 'Computadora', 1, datetime('now','localtime')) ");
        sqLiteDatabase.execSQL("insert into tb_productos values(1, 'Samsung Galaxy S6+', 255.0, 1), (2, 'Galaxy Tab S7+', 300.10, 2), (3, 'Laptop Toshiba Satellite A135-s2276', 599, 3)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists articulos");
        onCreate(sqLiteDatabase);
    }

    public SQLiteDatabase sqLiteDatabase() {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase;
    }

    public boolean InserTradicional(Dto datos) {
        boolean estado = true;
        int resultado;
        try {
            int codigo = datos.getCodigo();
            String descripcion = datos.getDescripcion();
            double precio = datos.getPrecio();

            Cursor fila = sqLiteDatabase().rawQuery("select codigo from articulos where codigo =" +
                    " '" + codigo + "'", null);

            if (fila.moveToFirst() == true) {
                estado = false;
            } else {
                String SQL = "INSERT INTO articulos \n" +
                        "(codigo, descripcion, precio)\n" +
                        "VALUES \n" + "('" + String.valueOf(codigo) + "','" + descripcion + "','" +
                        String.valueOf(precio) + "')";

                sqLiteDatabase().execSQL(SQL);
                sqLiteDatabase().close();

                estado = true;
            }
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());

        }
        return estado;
    }

    public boolean insertasrdatos(Dto datos) {
        boolean estado = true;
        int resultado;
        ContentValues registro = new ContentValues();
        try {
            registro.put("codigo", datos.getCodigo());
            registro.put("descripcion", datos.getDescripcion());
            registro.put("precio", datos.getPrecio());

            Cursor fila = sqLiteDatabase().rawQuery("select codigo from articulos where codigo = '"
                    + datos.getCodigo() + "'", null);

            if (fila.moveToFirst() == true) {
                estado = false;
            } else {
                resultado = (int) sqLiteDatabase().insert("articulos", null, registro);
                if (resultado > 0) estado = true;
                else estado = false;
            }
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public boolean InsertRegister(Dto datos){
        boolean estado = true;
        int resultado;
        try{
            int codigo = datos.getCodigo();
            String descripcion = datos.getDescripcion();
            double precio = datos.getPrecio();

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fecha1 = sdf.format(cal.getTime());

            Cursor fila = sqLiteDatabase().rawQuery("select codigo from articulos where codigo= '" + datos.getCodigo()+ "'", null);
            if(fila.moveToFirst()==true){
                estado = false;
            }else {
                String SQL = "INSERT INTO articulos \n" + "(codigo, descripcion, precio)\n" +
                        "VALUES \n" +
                        "(?, ?, ?);";

                sqLiteDatabase().execSQL(SQL, new String[]{String.valueOf(codigo),
                        descripcion, String.valueOf(precio)});
                estado = true;
            }
        }catch(Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public boolean consultaCodigo(Dto datos){
        boolean estado = true;
        int resultado;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try{
            int codigo = datos.getCodigo();

            Cursor fila = sqLiteDatabase.rawQuery("select codigo, descripcion, precio from articulos where codigo =" + codigo, null);
            if (fila.moveToFirst()){
                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setDescripcion(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));
                estado = true;
            }else {
                estado = false;

            }
            sqLiteDatabase.close();
        }catch (Exception e){
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public boolean consultaArticulos(Dto datos){
        boolean estado = true;
        int resultado;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        try {
            String[] parametros = {String.valueOf(datos.getCodigo())};
            String[] campos = {"codigo", "descripcion", "precio"};
            Cursor fila = sqLiteDatabase.query("articulos", campos,"codigo=?", parametros, null, null,null);
            if (fila.moveToFirst()){
                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setDescripcion(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));
                estado = true;
            }else {
                estado = true;
            }
            fila.close();;
            sqLiteDatabase.close();
        }catch (Exception e){
            estado = false;
            Log.e("error.", e.toString());
        }

        return estado;
    }

    public boolean consultarDescripcion(Dto datos){
        boolean estado = true;
        int resultado;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            String descripcion = datos.getDescripcion();
            Cursor fila = sqLiteDatabase.rawQuery("select codigo, descripcion, precio from articulos where descripcion = '" + descripcion + "'",null);
            if (fila.moveToFirst()){
                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setDescripcion(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));
                estado = true;
            }else   {
                estado = false;
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            estado = false;
            Log.e("error", e.toString());
        }

        return estado;
    }

    public boolean bajaCodigo(final Context context, final Dto datos){
        estadoDelete = true;

        try {
            int codigo = datos.getCodigo();
            Cursor fila = sqLiteDatabase().rawQuery("select * from articulos where codigo=" + codigo, null);
            if (fila.moveToFirst()){
                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setDescripcion(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.ic_delete);
                builder.setTitle("Warning");
                builder.setMessage("¿Esta seguro de borrar el registro? \nCódigo: " +
                        datos.getCodigo()+"\nDescripción: "+datos.getDescripcion());
                builder.setCancelable(false);
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int codigo = datos.getCodigo();
                        int cant = sqLiteDatabase().delete("articulos", "codigo=" + codigo, null);
                        if (cant > 0){
                            estadoDelete = true;
                            Toast.makeText(context, "Registro eliminado satisfactoriamente. ", Toast.LENGTH_SHORT).show();
                        } else  {
                            estadoDelete = false;
                        }
                        sqLiteDatabase().close();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }else {
                Toast.makeText(context, "No hay resutados encontrados para la busqueda especificada.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            estadoDelete = false;
            Log.e("Error.", e.toString());
        }
        return estadoDelete;
    }


    public boolean modificar(Dto datos){
        boolean estado = true;
        int resultado;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            int codigo = datos.getCodigo();
            String descripcion = datos.getDescripcion();
            double precio = datos.getPrecio();

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);
            int cant = (int)sqLiteDatabase.update("articulos", registro, "codigo=" + codigo, null);

            sqLiteDatabase.close();

            if (cant>0) estado = true;
            else estado = false;
        }catch (Exception e){
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public ArrayList<Dto> consultaListaArticulos(){
        boolean estado = false;
        SQLiteDatabase bd = this.getReadableDatabase();

        Dto articulos = null;
        articulosList = new ArrayList<Dto>();

        try{
            Cursor fila = bd.rawQuery("select * from articulos", null);
            while (fila.moveToNext()){
                articulos = new Dto();
                articulos.setCodigo(fila.getInt(0));
                articulos.setDescripcion(fila.getString(1));
                articulos.setPrecio(fila.getDouble(2));

                articulosList.add(articulos);

                Log.i("codigo", String.valueOf(articulos.getCodigo()));
                Log.i("descripcion", articulos.getDescripcion().toString());
                Log.i("precio", String.valueOf(articulos.getPrecio()));
            }

            obtenerListaArticulos();
        }catch (Exception e){

        }
        return articulosList;
    }

    public ArrayList<String> obtenerListaArticulos() {
        listaArticulos = new ArrayList<>();
        listaArticulos.add("Seleccione");

        for (int i=0; i<articulosList.size(); i++){
            listaArticulos.add(articulosList.get(i).getCodigo()+"~" + articulosList.get(i).getDescripcion());
        }
        return listaArticulos;
    }

    public ArrayList<String> consultaListaArticulos1(){
        boolean estado = false;
        SQLiteDatabase db  = this.getReadableDatabase();

        Dto articulos = null;
        articulosList = new ArrayList<Dto>();

        try {
            Cursor fila = db.rawQuery("select * from articulos", null);
            while (fila.moveToNext()){
                articulos = new Dto();
                articulos.setCodigo(fila.getInt(0));
                articulos.setDescripcion(fila.getString(1));
                articulos.setPrecio(fila.getDouble(2));

                articulosList.add(articulos);
            }

            listaArticulos = new ArrayList<String>();

            for (int i = 0; i <= articulosList.size(); i++){
                listaArticulos.add(articulosList.get(i).getCodigo()+"~"+ articulosList.get(i).getDescripcion());
            }
        }catch (Exception e){

        }
        return listaArticulos;
    }

   /* public List<Dto> mostrarArticulos(){
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM articulos order by codigo desc",null);
        List<Dto> articulos = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                articulos.add(new Dto(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2)));
            } while (cursor.moveToFirst());
        }
        return articulos;
    }*/

}
