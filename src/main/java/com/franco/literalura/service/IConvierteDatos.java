package com.franco.literalura.service;

public interface IConvierteDatos {

    <T> T obtenerDatos(String json, Class<T> clase);
}
