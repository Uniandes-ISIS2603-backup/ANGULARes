/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookstore.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author ca.torrese
 */
@Entity
public class ClientEntity extends BaseEntity implements Serializable {

    private String name;

    private String usser;

    private String password;

    private String location;

    @PodamExclude
    @OneToMany()
    private List<BookEntity> books;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the user
     */
    public String getUsser() {
        return usser;
    }

    /**
     * @param usser the user to set
     */
    public void setUsser(String usser) {
        this.usser = usser;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the books
     */
    public List<BookEntity> getBooks() {
        return books;
    }

    /**
     * @param books the books to set
     */
    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

}
