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
package co.edu.uniandes.csw.bookstore.dtos;

import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.entities.ClientEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link ClientDTO} para manejar las relaciones entre los
 * ClientDTO y otros DTOs. 
 * @author ca.torrese
 */
public class ClientDetailDTO extends ClientDTO implements Serializable {

    // relación  cero o muchos author
    private List<BookDTO> books;

    public ClientDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param clientEntity La entidad de la cual se construye el DTO
     */
    public ClientDetailDTO(ClientEntity clientEntity) {
        super(clientEntity);
        if (clientEntity.getBooks()!= null) {
            books = new ArrayList<>();
            for (BookEntity entityBook : clientEntity.getBooks()) {
                books.add(new BookDTO(entityBook));
            }
        }

    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el libro.
     */
    @Override
    public ClientEntity toEntity() {
        ClientEntity clientEntity = super.toEntity();
        if (getBooks() != null) {
            List<BookEntity> booksEntity = new ArrayList<>();
            for (BookDTO dtoBook : getBooks()) {
                booksEntity.add(dtoBook.toEntity());
            }
            clientEntity.setBooks(booksEntity);
        } 
        return clientEntity;
    }

    /**
     * @return the books
     */
    public List<BookDTO> getBooks() {
        return books;
    }

    /**
     * @param books the books to set
     */
    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    
}
