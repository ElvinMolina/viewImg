package com.example.viewimg;

import android.graphics.Bitmap;

public class ItemLista {
    private Bitmap imagen;
    private String descripcion;

    public ItemLista(Bitmap imagen, String descripcion) {
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }
}