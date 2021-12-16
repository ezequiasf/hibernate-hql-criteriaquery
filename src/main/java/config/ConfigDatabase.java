/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author zectr
 */
public class ConfigDatabase {

    private static Configuration config;

    public static SessionFactory currentSession(String resource, Class... anothedClass) {
        config = new Configuration().configure(resource);
        addAnothed(anothedClass);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                applySettings(config.getProperties());
        SessionFactory factory = null;
        try{
           factory =  config.buildSessionFactory(builder.build());
        }catch(HibernateException t){
            System.err.println("Falhou em criar o objeto SessionFactory");
            throw new ExceptionInInitializerError(t.getMessage());
        }
        
        return factory;
        
    }

    public static void setAnothedClass(Class... anothedClass) {
        addAnothed(anothedClass);
    }

    private static void addAnothed(Class... anothedClass) {
        if (anothedClass != null) {
            for (Class c : anothedClass) {
                config.addAnnotatedClass(c);
            }
        }
    }
}
