package com.jhulianbatres.kinlapp.controller;


import com.jhulianbatres.kinlapp.entity.Client;
import com.jhulianbatres.kinlapp.repository.ClientRepository;
import com.jhulianbatres.kinlapp.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RestController = @Controller + @RequestBody
@RequestMapping("/api/clients")
//Todas las rutas en este controlador deben de empezar por /clients
public class ClientController {

    //Inyectamos el SERVICIO y NO el repositorio
    //El controlador solo debe de tener conexion con el servicio
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    //Como buena practica la inyeccion de dependencias se debe hacer por el constructor

    //Este responde peticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el codigo HTTP y el cuerpo
    public ResponseEntity<List<Client>> list(){
        List<Client> clients = clientService.listAll();
        //Delegamos el servicio
        return ResponseEntity.ok(clients);
        // 200 OK with the client list
    }

    //{DPIClient} es una variable de ruta (valor a buscar)
    @GetMapping("/{DPIClient}")
    public ResponseEntity<Client> searchByDPI(@PathVariable String DPIClient){
        //@PathVariable toma el valor de la URL y lo asigna al dpi
        return clientService.findByClientDpi(DPIClient)
                //Si Optional tiene valor, devuelve 200 ok con el cliente
                .map(ResponseEntity::ok)
                //Si Optional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/actives")
    public ResponseEntity<List<Client>> searchByState() {
        return ResponseEntity.ok(clientService.findByClientsStates());
    }


        //POST crear un nuevo cliente
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Client client){
        //El @RequestBody toma el JSON del cuerpo y lo convierte en un objeto de tipo Cliente
        //<?> significa que es un tipo generico que puede ser un Client o String
        try {
            Client newClient = clientService.save(client);
            //Intentamos guardar el cliente, pero puede lanzar una excepcion
            // de tipo IllegalArgumentException

            return new ResponseEntity<>(newClient, HttpStatus.CREATED);
            //201 CREATED(mucho mas especifico que el 200 para la creacion de un cliente)
        }catch (IllegalArgumentException e){
            //Si hay error de validacion
            //404 BAD REQUEST con el mensaje de error
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{DPIClient}")
    public ResponseEntity<Void> delete(@PathVariable String DPIClient){
        //ResonesEntity<Void>: No devuelve cuerpo en la respuesta
        try {
            if (!clientService.existByDpi(DPIClient)){
                return ResponseEntity.notFound().build();
                //404 si no existe
            }

            clientService.delete(DPIClient);
            return ResponseEntity.noContent().build();
            //204 NO CONTENT (Pero se ejecuto correctamente y no devuelve cuerpo)

        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }

    //Actualizar el cliente por medio del DPI
    @PutMapping("/{DPIClient}")
    public ResponseEntity<?> update(@PathVariable String DPIClient, @RequestBody Client client){
        try {

            if (!clientService.existByDpi(DPIClient)){
                //Verificar si existe antes de poder actualizar
                return ResponseEntity.notFound().build();
                //404 NOT FOUND
            }

            //Actualizamos el cliente pero esto puede lanzar una excepcion
            Client updatedClient = clientService.update(DPIClient,client);
            return ResponseEntity.ok(updatedClient);
            //200 OK Ciente Actualizado


        }catch (IllegalArgumentException e){
            //Error cuando los datos sean incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            //Posiblemente cualquier otro error como: cliente no encontrado, etc.
            //404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }

}
