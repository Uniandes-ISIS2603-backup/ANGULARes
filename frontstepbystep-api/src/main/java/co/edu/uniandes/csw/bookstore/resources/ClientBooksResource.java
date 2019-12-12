/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookstore.resources;

import co.edu.uniandes.csw.bookstore.dtos.BookDTO;
import co.edu.uniandes.csw.bookstore.ejb.BookLogic;
import co.edu.uniandes.csw.bookstore.ejb.ClientBooksLogic;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Estudiante
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientBooksResource {
    
    @Inject
    private ClientBooksLogic clientBooksLogic;
             
    @Inject
    private BookLogic bookLogic ;
    
    /**
     * Asocia un servicioOfrecido existente a un trabjador existente
     * @param clientsId identificador del trabajadoral que se le desea asociar el servicioOfrecido
     * @param booksId identificador del serivcioOfrecido que se desea asociar.
     * @return JSON - servicioOfrecido asociado
     */
    @POST
    @Path("{booksId: \\d+}")
    public BookDTO addBook(@PathParam("clientsId") Long clientsId, @PathParam("booksId") Long booksId) {
        
        if (bookLogic.getBook(booksId) == null) {
            throw new WebApplicationException("El recurso /books/" + booksId + " no existe.", 404);
        }
       return new BookDTO(clientBooksLogic.addBook(clientsId, booksId));
    }
    
    /**
     * Devuelve todos los seriviciosOfrecidos de un trabajador existente
     * @param clientsId id del trabajador del que se quieren los serviciosOfrecidos
     * @return JSONArray - los libros encontrados para el trabjador. 
     */
    @GET
    public List<BookDTO> getBooks(@PathParam("clientsId") Long clientsId)
    {
       return bookEntityToDTO(clientBooksLogic.getBooks(clientsId));  
       
    }
    
    /**
     * Devuelve el servicio ofrecido con el id dado para el id del trajbajador dado.
     * @param clientsId indentificador del trabajador
     * @param bookId indetificador del servicioOfrecido
     * @return el serivicio ofrecido pr el trbajador 
     * @throws BusinessLogicException no cumples las reh;as de negocio.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el serivicioOfrecido.
     */
    @GET
    @Path("{bookId: \\d+}")
    public BookDTO getBook(@PathParam("clientsId") Long clientsId, @PathParam("bookId") Long bookId) throws BusinessLogicException
    {
        if(bookLogic.getBook(bookId)==null)
        {
         throw new WebApplicationException("El recurso /SeriviciosOfrecidos/" + bookId    + " no existe.", 404);   
        }
        
        return new BookDTO(clientBooksLogic.getBook(clientsId, bookId));  
    }
    
    private List<BookDTO> bookEntityToDTO( List<BookEntity> entityList)
    {
        List<BookDTO> lista = new ArrayList<>();
        
        for (BookEntity entity:entityList)
        {
            lista.add(new BookDTO(entity));
        }
        
        return lista;     
    
    }
}
