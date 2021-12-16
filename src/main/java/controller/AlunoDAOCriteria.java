/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import model.Aluno;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author zectr
 */
public class AlunoDAOCriteria {
    
    private final SessionFactory factory;
    
    public AlunoDAOCriteria (SessionFactory fac){
        factory = fac;
    }
    
    //Pesquisar os alunos cadastrados
    public void listAlunosNome (){

        Session s = factory.openSession();
        Transaction t = null;
        CriteriaBuilder builder = s.getCriteriaBuilder();
        
        try{            
            t = s.beginTransaction();
            CriteriaQuery<String> criteria = builder.createQuery(String.class);
            Root<Aluno> root = criteria.from(Aluno.class);  
            
            criteria.select(root.get("nome"));        
            List<String> nomes = s.createQuery(criteria).getResultList();
            
            nomes.stream().forEach(System.out::println);
            t.commit();
        }
        catch(HibernateException e){
            if(t!=null){
                t.rollback();
            }
            System.err.println("Erro:"+e.getMessage());
        }finally{
            s.close();
        }
    }
    
    public List<Object[]> nomeIdadeAlunos(){
        Session s = factory.openSession();
        Transaction t = null;
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        List<Object[]> listaArray = null;
        try{
            t = s.beginTransaction();
            Root<Aluno> root = criteria.from(Aluno.class);
            
            Path<String> nomes = root.get("nome");
            Path<Integer> idades = root.get("idade");
            criteria.select(builder.array(nomes,idades));
            listaArray = s.createQuery(criteria).getResultList();
            t.commit();
        }catch(HibernateException e){
            if(t!=null)t.rollback();
            System.err.println("Erro:"+e.getMessage());
        } finally{
            s.close();
        }
        return listaArray;
    }
    
    
}
