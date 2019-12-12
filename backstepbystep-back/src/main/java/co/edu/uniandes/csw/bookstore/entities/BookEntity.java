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
package co.edu.uniandes.csw.bookstore.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un libro en la persistencia y permite su serialización
 *
 * @author ISIS2603
 */
@Entity
public class BookEntity extends BaseEntity implements Serializable {

    private String name;
    private String isbn;
    private String image;
    @Temporal(TemporalType.DATE)
    private Date publishDate;
    private String description;
    private boolean bestSeller;
    private boolean EsNuevo;
    private boolean EsDescuento;
    private double precio;

    @PodamExclude
    @ManyToOne
    private EditorialEntity editorial;

    @PodamExclude
    @OneToMany(mappedBy = "book", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ReviewEntity> reviews = new ArrayList<ReviewEntity>();

    @PodamExclude
    @ManyToMany
    private List<AuthorEntity> authors = new ArrayList<AuthorEntity>();

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
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the publishDate
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * @param publishDate the publishDate to set
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the bestSeller
     */
    public boolean isBestSeller() {
        return bestSeller;
    }

    /**
     * @param bestSeller the bestSeller to set
     */
    public void setBestSeller(boolean bestSeller) {
        this.bestSeller = bestSeller;
    }

    /**
     * @return the EsNuevo
     */
    public boolean isEsNuevo() {
        return EsNuevo;
    }

    /**
     * @param EsNuevo the EsNuevo to set
     */
    public void setEsNuevo(boolean EsNuevo) {
        this.EsNuevo = EsNuevo;
    }

    /**
     * @return the EsDescuento
     */
    public boolean isEsDescuento() {
        return EsDescuento;
    }

    /**
     * @param EsDescuento the EsDescuento to set
     */
    public void setEsDescuento(boolean EsDescuento) {
        this.EsDescuento = EsDescuento;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @return the editorial
     */
    public EditorialEntity getEditorial() {
        return editorial;
    }

    /**
     * @param editorial the editorial to set
     */
    public void setEditorial(EditorialEntity editorial) {
        this.editorial = editorial;
    }

    /**
     * @return the reviews
     */
    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    /**
     * @param reviews the reviews to set
     */
    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    /**
     * @return the authors
     */
    public List<AuthorEntity> getAuthors() {
        return authors;
    }

    /**
     * @param authors the authors to set
     */
    public void setAuthors(List<AuthorEntity> authors) {
        this.authors = authors;
    }

    

}
