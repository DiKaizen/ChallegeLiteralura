package com.franco.literalura.service;

import tools.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {

    public ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
            return objectMapper.readValue(json,clase);

    }
}
