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
package co.edu.uniandes.csw.bookstore.test.persistence;

import co.edu.uniandes.csw.bookstore.entities.ClientEntity;
import co.edu.uniandes.csw.bookstore.persistence.ClientPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de persistencia de Books
 *
 * @author ISIS2603
 */
@RunWith(Arquillian.class)
public class ClientPersistenceTest {

    @Inject
    private ClientPersistence p;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ClientEntity> data = new ArrayList<ClientEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClientEntity.class.getPackage())
                .addPackage(ClientPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from ClientEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ClientEntity entity = factory.manufacturePojo(ClientEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un CLient.
     */
    @Test
    public void createClientTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
        ClientEntity result = p.create(newEntity);

        Assert.assertNotNull(result);

        ClientEntity entity = em.find(ClientEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getUsser(), entity.getUsser());
        Assert.assertEquals(newEntity.getPassword(), entity.getPassword());
        Assert.assertEquals(newEntity.getLocation(), entity.getLocation());

    }

    /**
     * Prueba para consultar la lista de Clients.
     */
    @Test
    public void getClientsTest() {
        List<ClientEntity> list = p.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ClientEntity ent : list) {
            boolean found = false;
            for (ClientEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Client.
     */
    @Test
    public void getClientTest() {
        ClientEntity entity = data.get(0);
        ClientEntity newEntity = p.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
        Assert.assertEquals(entity.getUsser(), newEntity.getUsser());
        Assert.assertEquals(entity.getPassword(), newEntity.getPassword());
                Assert.assertEquals(newEntity.getLocation(), entity.getLocation());
    }

    /**
     * Prueba para eliminar un Client.
     */
    @Test
    public void deleteClientTest() {
        ClientEntity entity = data.get(0);
        p.delete(entity.getId());
        ClientEntity deleted = em.find(ClientEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Client.
     */
    @Test
    public void updateClientTest() {
        ClientEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);

        newEntity.setId(entity.getId());

        p.update(newEntity);

        ClientEntity resp = em.find(ClientEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
        Assert.assertEquals(newEntity.getUsser(), resp.getUsser());
        Assert.assertEquals(newEntity.getPassword(), resp.getPassword());
        Assert.assertEquals(newEntity.getLocation(), resp.getLocation());
    }

    /**
     * Prueba para consultasr un Client por User.
     */
    @Test
    public void findClientByUserTest() {
        ClientEntity entity = data.get(0);
        ClientEntity newEntity = p.findByUser(entity.getUsser());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getUsser(), newEntity.getUsser());

        newEntity = p.findByUser(null);
        Assert.assertNull(newEntity);
    }
}
