package com.es.diecines.error;

public class BaseDeDatosException extends RuntimeException{

    private static final String DESCRIPCION ="ERROR EN LA BASE DE DATOS";
    public BaseDeDatosException(String detalles){
        super(DESCRIPCION+". "+detalles);
    }
}
