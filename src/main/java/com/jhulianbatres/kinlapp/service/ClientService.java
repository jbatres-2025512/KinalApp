package com.jhulianbatres.kinlapp.service;

import com.jhulianbatres.kinlapp.entity.Client;
import com.jhulianbatres.kinlapp.repository.ClientRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/*
Anotacion que registra un bean como una Bean de Spring
Que la clase contiene la logica del negocio
*/
    @Service
    /*
        Por defecto todos los metodos de esta clase seran transaccionales
        una transaccion es que puede o no ocurrir algo
     */
    @Transactional
    public class ClientService implements IClientService {
        /*
           private: solo es accesible dentro de la misma clase
           final: No puede cambiar porque es constante
           ClienteRepository: El repositorio para acceder a la base de datos
           Inyeccion de dependencias ya que Spring nos da el repositorio
         */
        private final ClientRepository clientRepository;
        /*
        Cosntructor: este se ejecuta al crea un objeto
        Spring pasa el repositorio automaticamente(Inyeccion de dependencias)
         */
        public ClientService(ClientRepository clientRepository) {
            this.clientRepository = clientRepository;
            //Asignar el repositorio a nuestra variable de clase
        }
        // Indica que se esta implementando un metodo de la interfaz
        @Override
        // OPtimiza la consulta, solo la lectura, para que no bloquee la base de datos
        @Transactional(readOnly = true)
        public List<Client> listAll() {
            return clientRepository.findAll();
            //findAll es un metodo de Spring que hace el select * from Clientes
            //este metodo es de JPARepository
        }

        @Override
        public Client save(Client client) {
            /*
             *Metodo de guardar,crea un cliente
             * Acá es donde colocamos la logica del negocio Antes de guardar
             * Primero validamos el dato
             * */

            validateClient(client);

            if (client.getState()==0)
                client.setState(1);

            return clientRepository.save(client);
        }

        @Override
        public Optional<Client> findByClientDpi(String DPIClient) {
            return clientRepository.findById(DPIClient);
        }

        @Override
        public List<Client> findByClientsStates(){

            return clientRepository.findAll().stream().filter(clientState -> clientState.getState() !=0).collect(Collectors.toList());

        }


        @Override
        @PreAuthorize("hasRole('ADMIN')")
        public Client update(String DPIClient, Client client) {

            //Metodo para actualizar un cliente existente

            if (!clientRepository.existsById(DPIClient)){
                throw new RuntimeException("El cliente no se encontro con el DPI" + DPIClient);
                // Si no existe se lanza una excepcion(error controlado)
            }

            client.setDPIClient(DPIClient);
            //Asegurarnos que el DPI del objeto coincida con el del URL
            //Por seguridad usamos el DPI de la URL y no el que viene en el JSON
            validateClient(client);

            return clientRepository.save(client);
            /*
             * save() este no solo sirve para guardar sino tambien para actualizar Si el dato
             * existe (dpi) entonces hace UPDATE pero si no existe hace un INSERT
             * pero antes se verifica si existe o no el registro
             */
        }

        @Override
        public void delete(String DPIClient) {
            //Eliminar un cliente
            if (!clientRepository.existsById(DPIClient)){
                throw new RuntimeException("El cliente no se encontro con el DPI"+DPIClient);
            }
            clientRepository.deleteById(DPIClient);
        }

        @Override
        @Transactional(readOnly = true)
        public boolean existByDpi(String DPIClient) {
            //Verificar si existe un cliente

            return clientRepository.existsById(DPIClient);
        }

        //Metodo privado(solamente se puede utilizar dentro de la clase)
        private void validateClient(Client client){
            /*
             * Validaciones del negocio: Este metodo se hará privado porque
             * es algo interno del servicio
             */

            if (client.getDPIClient()==null || client.getDPIClient().trim().isEmpty()){
                //Si el DPI es null o está vacío despues de quitar espacios
                //lanza una excepcion con un mensaje
                throw new IllegalArgumentException("El DPI es un dato obligatorio");
            }

            if (client.getNameClient()==null || client.getNameClient().trim().isEmpty()){
                throw new IllegalArgumentException("El nombre es un dato obligatorio");
            }

            if (client.getLastNameClient()==null || client.getLastNameClient().trim().isEmpty()){
                throw new IllegalArgumentException("El apellido es un dato obligatorio");
            }



        }



    }

