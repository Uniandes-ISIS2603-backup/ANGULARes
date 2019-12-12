/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookstore.ejb;

import co.edu.uniandes.csw.bookstore.entities.ClientEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.persistence.ClientPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Estudiante
 */
@Stateless
public class ClientLogic {
    
      private static final Logger LOGGER = Logger.getLogger(ClientLogic.class.getName());

    @Inject
    private ClientPersistence persistence;

    /**
     * Guardar un nuevo cliente
     *
     * @param clientEntity La entidad de tipo libro del nuevo libro a persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException Si el ISBN es inválido o ya existe en la
     * persistencia.
     */
    public ClientEntity createClient(ClientEntity clientEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del cliente");
        if (clientEntity.getName() == null || clientEntity.getName().equals("")) {
            throw new BusinessLogicException("El nombre es inválido");
        }
        if (clientEntity.getUsser() == null || clientEntity.getUsser().equals("")) {
            throw new BusinessLogicException("El nombre es inválido");
        }
        if (clientEntity.getPassword() == null || clientEntity.getPassword().equals("")) {
            throw new BusinessLogicException("El contrasena es inválido");
        }
        if (persistence.findByUser(clientEntity.getUsser()) != null) {
            throw new BusinessLogicException("El usuario ya existe");
        }
        persistence.create(clientEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del cliente");
        return clientEntity;
    }

    /**
     * Devuelve todos los clientes que hay en la base de datos.
     *
     * @return Lista de entidades de tipo cliente.
     */
    public List<ClientEntity> getClients() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los clientes");
        List<ClientEntity> clientes = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los clientes");
        return clientes;
    }

    /**
     * Busca un cliente por ID
     *
     * @param clientsId El id del cliente a buscar
     * @return El cliente encontrado, null si no lo encuentra.
     */
    public ClientEntity getClient(Long clientsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el libro con id = {0}", clientsId);
        ClientEntity clientEntity = persistence.find(clientsId);
        if (clientEntity == null) {
            LOGGER.log(Level.SEVERE, "El libro con el id = {0} no existe", clientEntity);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el libro con id = {0}", clientEntity);
        return clientEntity;
    }

    /**
     * Actualizar un cliente por ID
     *
     * @param clientsId El ID del libro a actualizar
     * @param clientEntity La entidad del libro con los cambios deseados
     * @return La entidad del libro luego de actualizarla
     * @throws BusinessLogicException Si el IBN de la actualización es inválido
     */
    public ClientEntity updateClient(Long clientsId, ClientEntity clientEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el libro con id = {0}", clientsId);
        if (clientEntity.getName() == null || clientEntity.getName().equals("")) {
            throw new BusinessLogicException("El nombre es inválido");
        }
         if (clientEntity.getPassword() == null || clientEntity.getPassword().equals("")) {
            throw new BusinessLogicException("El contrasena es inválido");
        }
        ClientEntity newEntity = persistence.update(clientEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el libro con id = {0}", clientEntity.getId());
        return newEntity;
    }

    /**
     * Eliminar un cliente por ID
     *
     * @param clientsId El ID del libro a eliminar
     * @throws BusinessLogicException si el libro tiene autores asociados
     */
    public void deleteClient(Long clientsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el libro con id = {0}", clientsId);
        persistence.delete(clientsId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el libro con id = {0}", clientsId);
    }

    
}
