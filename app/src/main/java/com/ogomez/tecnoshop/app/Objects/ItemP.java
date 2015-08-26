package com.ogomez.tecnoshop.app.Objects;

import java.io.File;

/**
 * Created by Oswaldo Gomez on 26/07/2015.
 */
public class ItemP {

    int id;
    int id_user;
    String nombre;
    String categoria;
    String descripcion;
    double precio;
    int local;
    String path;
    String inserted;
    int status;
    File img;

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getLocal() {
        return local;
    }

    public void setLocal(int local) {
        this.local = local;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getInserted() {
        return inserted;
    }

    public void setInserted(String inserted) {
        this.inserted = inserted;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }





}
