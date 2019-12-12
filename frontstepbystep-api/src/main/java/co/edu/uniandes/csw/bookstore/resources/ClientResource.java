/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.bookstore.resources;

import co.edu.uniandes.csw.bookstore.dtos.ClientDTO;
import co.edu.uniandes.csw.bookstore.dtos.ClientDetailDTO;
import co.edu.uniandes.csw.bookstore.ejb.BookEditorialLogic;
import co.edu.uniandes.csw.bookstore.ejb.BookLogic;
import co.edu.uniandes.csw.bookstore.ejb.ClientLogic;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.entities.ClientEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.bookstore.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "books".
 *
 * @author ISIS2603
 * @version 1.0
 */
@Path("clientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClientResource {

    private static final Logger LOGGER = Logger.getLogger(ClientResource.class.getName());

    @Inject
    private ClientLogic clientLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    
    /**
     * Crea un nuevo libro con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param client {@link BookDTO} - EL libro que se desea guardar.
     * @return JSON {@link BookDTO} - El libro guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el libro o el isbn es
     * inválido o si la editorial ingresada es invalida.
     */
    @POST
    public ClientDTO createClient(ClientDTO client) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "BookResource createBook: input: {0}", client);
        ClientDTO nuevoClientDTO = new ClientDTO(clientLogic.createClient(client.toEntity()));
        LOGGER.log(Level.INFO, "BookResource createBook: output: {0}", nuevoClientDTO);
        return nuevoClientDTO;
    }

    /**
     * Busca y devuelve todos los libros que existen en la aplicacion.
     *
     * @return JSONArray {@link BookDetailDTO} - Los libros encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ClientDTO> getClients() {
        LOGGER.info("BookResource getBooks: input: void");
        List<ClientDTO> listaClients = listEntity2DetailDTO(clientLogic.getClients());
        LOGGER.log(Level.INFO, "BookResource getBooks: output: {0}", listaClients);
        return listaClients;
    }

    /**
     * Busca el libro con el id asociado recibido en la URL y lo devuelve.
     *
     * @param clientsId Identificador del libro que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BookDetailDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @GET
    @Path("{clientsId: \\d+}")
    public ClientDetailDTO getClient(@PathParam("clientsId") Long clientsId) {
        LOGGER.log(Level.INFO, "BookResource getBook: input: {0}", clientsId);
        ClientEntity clientEntity = clientLogic.getClient(clientsId);
        if (clientEntity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientsId + " no existe.", 404);
        }
        ClientDetailDTO clientDetailDTO = new ClientDetailDTO(clientEntity);
        LOGGER.log(Level.INFO, "BookResource getBook: output: {0}", clientDetailDTO);
        return clientDetailDTO;
    }

    /**
     * Actualiza el libro con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param clientsId Identificador del libro que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @param client {@link BookDTO} El libro que se desea guardar.
     * @return JSON {@link BookDetailDTO} - El libro guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el libro.
     */
    @PUT
    @Path("{clientsId: \\d+}")
    public ClientDetailDTO updateClient(@PathParam("clientsId") Long clientsId, ClientDetailDTO client) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "BookResource updateBook: input: id: {0} , book: {1}", new Object[]{clientsId, client});
        client.setId(clientsId);
        if (clientLogic.getClient(clientsId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientsId + " no existe.", 404);
        }
        ClientDetailDTO detailDTO = new ClientDetailDTO(clientLogic.updateClient(clientsId, client.toEntity()));
        LOGGER.log(Level.INFO, "BookResource updateBook: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Borra el libro con el id asociado recibido en la URL.
     *
     * @param clientsId Identificador del libro que se desea borrar. Este debe ser
     * una cadena de dígitos.
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     * cuando el libro tiene autores asociados.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @DELETE
    @Path("{clientsId: \\d+}")
    public void deleteBook(@PathParam("clientsId") Long clientsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "BookResource deleteBook: input: {0}", clientsId);
        ClientEntity entity = clientLogic.getClient(clientsId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + clientsId + " no existe.", 404);
        }
        clientLogic.deleteClient(clientsId);
        LOGGER.info("BookResource deleteBook: output: void");
    }

    /**
     * Conexión con el servicio de reseñas para un libro. {@link ReviewResource}
     *
     * Este método conecta la ruta de /books con las rutas de /reviews que
     * dependen del libro, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las reseñas.
     *
     * @param clientsId El ID del libro con respecto al cual se accede al
     * servicio.
     * @return El servicio de Reseñas para ese libro en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
   // @Path("{clientsId: \\d+}/reviews")
   // public Class<ReviewResource> getReviewResource(@PathParam("clientsId") Long clientsId) {
   //     if (clientLogic.getClient(booksId) == null) {
     //       throw new WebApplicationException("El recurso /books/" + booksId + "/reviews no existe.", 404);
     //   }
     //   return ReviewResource.class;
    //}

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos BookEntity a una lista de
     * objetos BookDetailDTO (json)
     *
     * @param entityList corresponde a la lista de libros de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de libros en forma DTO (json)
     */
    private List<ClientDTO> listEntity2DetailDTO(List<ClientEntity> entityList) {
        List<ClientDTO> list = new ArrayList<>();
        for (ClientEntity entity : entityList) {
            list.add(new ClientDTO(entity));
        }
        return list;
    }
}

