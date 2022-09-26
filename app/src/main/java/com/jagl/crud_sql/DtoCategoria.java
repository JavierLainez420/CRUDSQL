package com.jagl.crud_sql;

import java.util.Date;

public class DtoCategoria {
    int idcategoria;
    String nombrecategoria;
    int estadocategoria;
    String fecharegistro;

    public DtoCategoria() {
    }

    public DtoCategoria(int idcategoria, String nombrecategoria, int estadocategoria, String fecharegistro) {
        this.idcategoria = idcategoria;
        this.nombrecategoria = nombrecategoria;
        this.estadocategoria = estadocategoria;
        this.fecharegistro = fecharegistro;
    }


    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombrecategoria() {
        return nombrecategoria;
    }

    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
    }

    public int getEstadocategoria() {
        return estadocategoria;
    }

    public void setEstadocategoria(int estadocategoria) {
        this.estadocategoria = estadocategoria;
    }

    public String getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(String  fecharegistro) {
        this.fecharegistro = fecharegistro;
    }
}
