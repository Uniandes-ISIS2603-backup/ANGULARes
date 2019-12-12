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
package co.edu.uniandes.csw.bookstore.test.logic;

import co.edu.uniandes.csw.bookstore.ejb.ClientLogic;
import co.edu.uniandes.csw.bookstore.entities.ClientEntity;
import co.edu.uniandes.csw.bookstore.persistence.ClientPersistence;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
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
 * Pruebas de logica de Books
 *
 * @author ca.torrese
 */
@RunWith(Arquillian.class)
public class ClientLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ClientLogic clientLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

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
                .addPackage(ClientLogic.class.getPackage())
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
        
        for (int i = 0; i < 3; i++) {
            ClientEntity entity = factory.manufacturePojo(ClientEntity.class);

            em.persist(entity);
            data.add(entity);
        }
       
    }

    /**
     * Prueba para crear un Book
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void createClientTest() throws BusinessLogicException {
        ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
        ClientEntity result = clientLogic.createClient(newEntity);
        Assert.assertNotNull(result);
        ClientEntity entity = em.find(ClientEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getUsser(), entity.getUsser());
        Assert.assertEquals(newEntity.getPassword(), entity.getPassword());
        Assert.assertEquals(newEntity.getLocation(), entity.getLocation());
    }

    /**
     * Prueba para crear un Book con ISBN inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClientTestConNameInvalido() throws BusinessLogicException {
        ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
        newEntity.setName("");
        clientLogic.createClient(newEntity);
    }

    /**
     * Prueba para crear un Book con ISBN inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClientTestConNameInvalido2() throws BusinessLogicException {
        ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
        newEntity.setName(null);
        clientLogic.createClient(newEntity);
    }
    
    /**
     * Prueba para crear un Book con ISBN inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClientTestConUsserInvalido() throws BusinessLogicException {
        ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
        newEntity.setUsser("");
        clientLogic.createClient(newEntity);
    }

    /**
     * Prueba para crear un Book con ISBN inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClientTestConUsserInvalido2() throws BusinessLogicException {
        ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
        newEntity.setUsser(null);
        clientLogic.createClient(newEntity);
    }
    
    /**
     * Prueba para crear un Book con ISBN inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClientTestConLocationInvalido() throws BusinessLogicException {
        ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
        newEntity.setLocation("");
        clientLogic.createClient(newEntity);
    }
    
    /**
     * Prueba para crear un Book con ISBN inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClientTestConLocationInvalido2() throws BusinessLogicException {
        ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
        newEntity.setLocation(null);
        clientLogic.createClient(newEntity);
    }
    
    /**
     * Prueba para crear un Book con ISBN inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClientTestConPasswordInvalido() throws BusinessLogicException {
        ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
        newEntity.setPassword("");
        clientLogic.createClient(newEntity);
    }

    /**
     * Prueba para crear un Book con ISBN inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClientTestConPasswordInvalido2() throws BusinessLogicException {
        ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
        newEntity.setPassword(null);
        clientLogic.createClient(newEntity);
    }

    /**
     * Prueba para crear un Book con ISBN existente.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClientTestConUsserExistente() throws BusinessLogicException {
        ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
        newEntity.setUsser(data.get(0).getUsser());
        clientLogic.createClient(newEntity);
    }

    /**
     * Prueba para consultar la lista de Books.
     */
    @Test
    public void getClientsTest() {
        List<ClientEntity> list = clientLogic.getClients();
        Assert.assertEquals(data.size(), list.size());
        for (ClientEntity entity : list) {
            boolean found = false;
            for (ClientEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Book.
     */
    @Test
    public void getClientTest() {
        ClientEntity entity = data.get(0);
        ClientEntity resultEntity = clientLogic.getClient(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getLocation(), resultEntity.getLocation());
        Assert.assertEquals(entity.getPassword(), resultEntity.getPassword());
        Assert.assertEquals(entity.getUsser(), resultEntity.getUsser());
    }

    /**
     * Prueba para actualizar un Book.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void updateClientTest() throws BusinessLogicException {
        ClientEntity entity = data.get(0);
        ClientEntity pojoEntity = factory.manufacturePojo(ClientEntity.class);
        pojoEntity.setId(entity.getId());
        clientLogic.updateClient(pojoEntity.getId(), pojoEntity);
        ClientEntity resp = em.find(ClientEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getLocation(), resp.getLocation());
        Assert.assertEquals(pojoEntity.getPassword(), resp.getPassword());
        Assert.assertEquals(pojoEntity.getUsser(), resp.getUsser());
    }

    /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateClientConNameInvalidoTest() throws BusinessLogicException {
        ClientEntity entity = data.get(0);
        ClientEntity pojoEntity = factory.manufacturePojo(ClientEntity.class);
        pojoEntity.setName("");
        pojoEntity.setId(entity.getId());
        clientLogic.updateClient(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateClientConNameInvalidoTest2() throws BusinessLogicException {
        ClientEntity entity = data.get(0);
        ClientEntity pojoEntity = factory.manufacturePojo(ClientEntity.class);
        pojoEntity.setName(null);
        pojoEntity.setId(entity.getId());
        clientLogic.updateClient(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateClientConPasswordInvalidoTest() throws BusinessLogicException {
        ClientEntity entity = data.get(0);
        ClientEntity pojoEntity = factory.manufacturePojo(ClientEntity.class);
        pojoEntity.setPassword("");
        pojoEntity.setId(entity.getId());
        clientLogic.updateClient(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateClientConPasswordInvalidoTest2() throws BusinessLogicException {
        ClientEntity entity = data.get(0);
        ClientEntity pojoEntity = factory.manufacturePojo(ClientEntity.class);
        pojoEntity.setPassword(null);
        pojoEntity.setId(entity.getId());
        clientLogic.updateClient(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateClientConLocationInvalidoTest() throws BusinessLogicException {
        ClientEntity entity = data.get(0);
        ClientEntity pojoEntity = factory.manufacturePojo(ClientEntity.class);
        pojoEntity.setLocation("");
        pojoEntity.setId(entity.getId());
        clientLogic.updateClient(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateClientConLocationInvalidoTest2() throws BusinessLogicException {
        ClientEntity entity = data.get(0);
        ClientEntity pojoEntity = factory.manufacturePojo(ClientEntity.class);
        pojoEntity.setLocation(null);
        pojoEntity.setId(entity.getId());
        clientLogic.updateClient(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para eliminar un Book.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteClientTest() throws BusinessLogicException {
        ClientEntity entity = data.get(0);
        clientLogic.deleteClient(entity.getId());
        ClientEntity deleted = em.find(ClientEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}

