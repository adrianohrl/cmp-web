package tech.adrianohrl.stile.control.bean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class DataSource {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("stile-webPU");

    private DataSource() {
    }

    public static EntityManager createEntityManager() {
        return emf.createEntityManager();
    }
    
    public static void closeEntityManagerFactory() {
        emf.close();
    }
    
}
