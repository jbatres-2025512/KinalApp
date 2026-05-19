package com.jhulianbatres.kinlapp.service;

import com.jhulianbatres.kinlapp.entity.Client;

import java.util.List;
import java.util.Optional;

public interface IClientService {

    /*
     *  Interfaz: es un contrato que dice Que metodos debe tener
     *  Cualquier servicio de Clientes, No tiene
     *  IMplementacion, solo la definicion de los metodos
     */
    //Metodo que devuelve una lista de todos los clientes
    List<Client> listAll();
    /*
     * List <>lo que hace es devolver una lista
     * de objetos de la entidad Clientes
     */


    //Metodo que Guarda un CLiente en la base de datos
    Client save(Client client);
    //Párametros: recibe un objeto cliente con los datos a guardar

    //Optional - contenedor que puede o no tener valor
    //Evita el error de NUllPointerException
    Optional<Client> findByClientDpi(String dpiClient);

    List<Client> findByClientsStates();

    //Metodo que actualiza un Cliente
    Client update(String dpi, Client client);
    /*
     *Parametros - dpi: DPI a acrializar
     *Cliente cliente: Objeto con los datos nuevos
     * Retorna un objeto de tipo CLiente ya actualizado
     * */

    /*
        Metodo de tipo void para eliminar un cliente
        void: no retona ningun valor o datos
        Elimina un Cliente por su DPI
     */
    void delete(String dpiClient);


    //boolean - Retornara true si existe y false si no exite
    boolean existByDpi(String dpiClient);



}
