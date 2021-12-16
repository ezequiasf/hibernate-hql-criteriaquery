/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import constants.CursosFacul;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.persistence.Query;
import model.Aluno;
import model.Curso;
import org.hibernate.HibernateException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


/**
 *
 * @author zectr
 */
//CRUD UTILIZANDO HQL
public class AlunoDAOHql {

    private SessionFactory sessao;

    public AlunoDAOHql(SessionFactory session) {
        this.sessao = session;
    }

    public void setSessaoF(SessionFactory sessao) {
        this.sessao = sessao;
    }

    public Integer addAluno(String nome, int idade, CursosFacul tipoCurso) {
        List<Curso> cursos = listaCursos();
        Boolean existeCurso = isCursoExiste(tipoCurso);
        Session s = sessao.openSession();
        Integer alunoId = null;
        Transaction t = null;

        try {
            t = s.beginTransaction();
            Function<Curso, Integer> alunoCursoExis = cur -> {
                Aluno a = new Aluno(nome, idade, cur);
                return (Integer) s.save(a);
            };

            if (!existeCurso) {
                Curso c = new Curso(tipoCurso);
                s.save(c);
                Aluno a = new Aluno(nome, idade, c);
                alunoId = (Integer) s.save(a);
            } else {
                alunoId = cursos.stream().filter(checagemCurso(tipoCurso))
                        .map(alunoCursoExis).reduce(0, (i, x) -> i + x);
            }

            t.commit();

        } catch (HibernateException ex) {
            seOcorrerErro("Aconteceu o erro ao tentar salvar o aluno:", t, ex);
        } finally {
            s.close();
        }
        return alunoId;
    }
    
    public List searchBetweenIdade (int val1, int val2){
        Session s = sessao.openSession();
        Transaction t = null;
        String betweenQuery = "FROM Aluno Where idade Between :val1 and :val2";
        List<Aluno> alunos = null;
        
        try{
            t = s.beginTransaction();
            
            Query q = s.createQuery(betweenQuery);
            q.setParameter("val1", val1);
            q.setParameter ("val2", val2);
            alunos = q.getResultList();
            t.commit();
        }catch(HibernateException e){
            seOcorrerErro("Erro",t,e);
        }finally{
            s.close();
        }
        return alunos;
    }
    
    public List searchAlunos() {
        Session s = sessao.openSession();
        Transaction t = null;
        List alunos = null;

        try {
            t = s.beginTransaction();
            alunos = s.createQuery("FROM Aluno").list();
            t.commit();
        } catch (HibernateException e) {
            seOcorrerErro("Aconteceu o erro ao tentar buscar os aluno:", t, e);
        } finally {
            s.close();
        }
        return alunos;
    }

    public void updateAluno(Integer alunoId, CursosFacul novoCurso) {
        List<Curso> cursos = listaCursos();
        Boolean existeCurso = isCursoExiste(novoCurso);
        Session s = sessao.openSession();
        Transaction t = null;

        try {
            t = s.beginTransaction();
            Aluno a = s.get(Aluno.class, alunoId);
            //Para caso a tabela esteja vazia;
            if (!existeCurso) {
                Curso c = new Curso(novoCurso);
                s.save(c);
                a.setCurso(c);
                s.update(a);
            } else {
                cursos.stream().filter(checagemCurso(novoCurso))
                        .forEach(c -> {
                            a.setCurso(c);
                            s.update(a);
                        });
            }

            t.commit();
        } catch (HibernateException ex) {
            seOcorrerErro("Aconteceu o erro ao tentar atualizar o aluno:", t, ex);
        } finally {
            s.close();
        }
    }

    public void deleteAluno(Integer id) {
        Session s = sessao.openSession();
        Transaction t = null;

        try {
            t = s.beginTransaction();
            Aluno a = s.get(Aluno.class, id);
            //Se apagasse o curso diretamente, o aluno também seria exclúido.
            //Como o aluno (dependente) está sendo apagado, o curso continua "vivo".
            if(a!=null)s.delete(a);
            t.commit();
        } catch (HibernateException ex) {
            seOcorrerErro("Aconteceu o erro ao tentar atualizar o aluno:", t, ex);
        } finally {
            s.close();
        }

    }

    private Boolean isCursoExiste(CursosFacul curso) {
        
        List<Curso> cursos = listaCursos();
        Session s = sessao.openSession();
        Transaction t = null;
        Boolean existe = null;

        try {
            t = s.beginTransaction();
            existe = cursos.stream().anyMatch(checagemCurso(curso));
            t.commit();
        } catch (HibernateException e) {
            seOcorrerErro("Erro", t, e);
        } finally {
            s.close();
        }
        return existe;
    }

    private Predicate<Curso> checagemCurso(CursosFacul curso) {
        Predicate<Curso> checagemCurso = c -> c.getCurso().equals(curso.getCurso());
        return checagemCurso;
    }

    public List<Curso> listaCursos() {
        Session s = sessao.openSession();
        Transaction t = null;
        List<Curso> cursos = null;

        try {
            t = s.beginTransaction();
            cursos = s.createQuery("FROM Curso").list();
            t.commit();
        } catch (HibernateException e) {
            seOcorrerErro("Erro", t, e);
        } finally {
            s.close();
        }
        return cursos;
    }

    private void seOcorrerErro(String textoErro, Transaction t, Exception e) {
        if (t != null) {
            t.rollback();
        }
        System.err.println(textoErro + e.getMessage());
    }
    
}
