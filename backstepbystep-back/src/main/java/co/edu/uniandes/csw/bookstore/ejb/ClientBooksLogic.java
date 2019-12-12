/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookstore.ejb;

import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.entities.ClientEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.persistence.BookPersistence;
import co.edu.uniandes.csw.bookstore.persistence.ClientPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Estudiante
 */
@Stateless
public class ClientBooksLogic {
    
    @Inject
    private ClientPersistence clientPersistence;
    
    @Inject
    private BookPersistence bookPersistence;
   /**
     * Asocia un Book existente a un Cliente
     *
     * @param clientsId Identificador de la instancia de Author
     * @param booksId Identificador de la instancia de Book
     * @return Instancia de BookEntity que fue asociada a Author
     */
    public BookEntity addBook(Long clientsId, Long booksId) {
        ClientEntity clientEntity = clientPersistence.find(clientsId);
        BookEntity bookEntity = bookPersistence.find(booksId);
        clientEntity.getBooks().add(bookEntity);
        return bookPersistence.find(booksId);
    } 
    
    /**
     * Obtiene una colección de instancias de ServiciosEntity asociadas a una
     * instancia de Author
     *
     * @param clientsId Identificador de la instancia de Trabajador
     * @return Colección de instancias ServicioOfrecidokEntity asociadas a la instancia de
     * Trabajador
     */
    public List<BookEntity> getBooks(Long clientsId) {
        return (List<BookEntity>) clientPersistence.find(clientsId).getBooks();
    }
    
    /**
     * Obtiene una instancia de ServicioOfrecidoEntity asociada a una instancia de Author
     *
     * @param clientId Identificador de la instancia de Author
     * @param bookId Identificador de la instancia de Book
     * @return La entidadd de Libro del autor
     * @throws BusinessLogicException Si el libro no está asociado al autor
     */
    public BookEntity getBook   (Long clientId, Long bookId) throws BusinessLogicException {
      
        List<BookEntity> servicios = (List<BookEntity>) clientPersistence.find(clientId).getBooks();
        for (BookEntity i: servicios)
        {
            if(i.getId().equals(bookId))
            {
                return i;
            }
        }

        throw new BusinessLogicException("El cliente no tiene el libro "+ bookId);
        
    }
}
