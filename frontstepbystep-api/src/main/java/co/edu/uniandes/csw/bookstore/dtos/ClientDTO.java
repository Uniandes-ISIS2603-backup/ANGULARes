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

import co.edu.uniandes.csw.bookstore.entities.ClientEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

 /*
 * @author ISIS2603
 */
public class ClientDTO implements Serializable {

    private Long id;
    private String name;
    private String usser;
    private String password;
    private String location;
    
    /**
     * Constructor por defecto
     */
    public ClientDTO() {
    }

    /**
     * Constructor a partir de la entidad
     *
     * @param clientEntity La entidad del libro
     */
    public ClientDTO(ClientEntity clientEntity) {
        if (clientEntity != null) {
            this.id = clientEntity.getId();
            this.name = clientEntity.getName();
            this.usser = clientEntity.getUsser();
            this.password = clientEntity.getPassword();
            this.location = clientEntity.getLocation();
        }
    }

    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad del libro asociado.
     */
    public ClientEntity toEntity() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(this.getId());
        clientEntity.setName(this.getName());
        clientEntity.setLocation(this.getLocation());
        clientEntity.setUsser(this.getUsser());
        clientEntity.setPassword(this.getPassword());
        return clientEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * @return the usser
     */
    public String getUsser() {
        return usser;
    }

    /**
     * @param usser the usser to set
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

}