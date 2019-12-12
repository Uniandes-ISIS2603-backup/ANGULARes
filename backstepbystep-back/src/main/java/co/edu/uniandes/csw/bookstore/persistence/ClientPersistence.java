/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookstore.persistence;

import co.edu.uniandes.csw.bookstore.entities.ClientEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Estudiante
 */
@Stateless
public class ClientPersistence {
     private static final Logger LOGGER = Logger.getLogger(BookPersistence.class.getName());

    @PersistenceContext(unitName = "BookStorePU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param clientEntity objeto libro que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ClientEntity create(ClientEntity clientEntity) {
        LOGGER.log(Level.INFO, "Creando un cliente nuevo");
        em.persist(clientEntity);
        LOGGER.log(Level.INFO, "Cliente creado");
        return clientEntity;
    }

    /**
     * Devuelve todos los clientes de la base de datos.
     *
     * @return una lista con todos los clientes que encuentre en la base de datos,
     * "select u from BookEntity u" es como un "select * from ClientEntity;" -
     * "SELECT * FROM table_name" en SQL.
     */
    public List<ClientEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los clientes");
        Query q = em.createQuery("select u from ClientEntity u");
        return q.getResultList();
    }

    /**
     * Busca si hay algun cliente con el id que se envía de argumento
     *
     * @param clientsId : id correspondiente al clienre buscado.
     * @return un cliente.
     */
    public ClientEntity find(Long clientsId) {
        LOGGER.log(Level.INFO, "Consultando el cliente con id={0}", clientsId);
        return em.find(ClientEntity.class, clientsId);
    }

    /**
     * Actualiza un cliente.
     *
     * @param clientEntity: el cliente que viene con los nuevos cambios. Por ejemplo
     * el nombre pudo cambiar. En ese caso, se haria uso del método update.
     * @return un cliente con los cambios aplicados.
     */
    public ClientEntity update(ClientEntity clientEntity) {
        LOGGER.log(Level.INFO, "Actualizando el cliente con id={0}", clientEntity.getId());
        return em.merge(clientEntity);
    }

    /**
     *
     * Borra un cliente de la base de datos recibiendo como argumento el id del
     * cliente
     *
     * @param clientsId : id correspondiente al cliente a borrar.
     */
    public void delete(Long clientsId) {
        LOGGER.log(Level.INFO, "Borrando el cliente con id={0}", clientsId);
        ClientEntity bookEntity = em.find(ClientEntity.class, clientsId);
        em.remove(bookEntity);
    }

    /**
     * Busca si hay algun cliente con el Usuario que se envía de argumento
     *
     * @param usser: Usuario del cliente
     * @return null si no existe ningun cliente con el usuario del argumento. Si
     * existe alguno devuelve el primero.
     */
    public ClientEntity findByUser(String usser) {
        LOGGER.log(Level.INFO, "Consultando clientes por usuario ", usser);
        TypedQuery query = em.createQuery("Select e From ClientEntity e where e.usser = :usser", ClientEntity.class);
        query = query.setParameter("usser", usser);
        // Se invoca el query se obtiene la lista resultado
        List<ClientEntity> sameUser = query.getResultList();
        ClientEntity result;
        if (sameUser == null) {
            result = null;
        } else if (sameUser.isEmpty()) {
            result = null;
        } else {
            result = sameUser.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar clientes por usuario ", usser);
        return result;
    }
}
