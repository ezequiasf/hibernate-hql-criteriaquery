/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exec;

import config.ConfigDatabase;
import constants.CursosFacul;
import controller.AlunoDAOCriteria;
import controller.AlunoDAOHql;
import org.hibernate.SessionFactory;

/**
 *
 * @author zectr
 */
public class Execute {
    public static void main(String[] args) {
       SessionFactory sf = ConfigDatabase.
                currentSession("hibernate.cfg.xml", model.Aluno.class,model.Curso.class);
        AlunoDAOHql adao = new AlunoDAOHql(sf);
        //adao.deleteAluno(4);
       // adao.listaCursos().stream().forEach(c->System.out.println(c.getCurso()));
        /*adao.addAluno("Maycon", 45, CursosFacul.FISICA);
        adao.addAluno("Henrique", 17, CursosFacul.MATEMATICA);
        adao.addAluno("Vinicius", 33, CursosFacul.MATEMATICA);
        adao.addAluno("kepler", 65, CursosFacul.FISICA);
        adao.addAluno("einstein", 12, CursosFacul.MATEMATICA);
        adao.addAluno("messi", 32, CursosFacul.MATEMATICA);
        adao.addAluno("neymar", 25, CursosFacul.FISICA);
        adao.addAluno("jorge", 37, CursosFacul.MATEMATICA);
        adao.addAluno("kleber", 28, CursosFacul.MATEMATICA);
        adao.addAluno("riquelme", 25, CursosFacul.FISICA);
        adao.addAluno("pedro", 43, CursosFacul.MATEMATICA);
        adao.addAluno("cpt nascimento", 13, CursosFacul.MATEMATICA);*/
       /*
        AlunoDAOCriteria acr = new AlunoDAOCriteria (sf);
        acr.nomeIdadeAlunos().stream().forEach(arr-> {
            System.out.println("Nome:"+arr[0]+"\n"+"Idade:"+arr[1]);
        } );*/
       adao.searchBetweenIdade(10, 32).stream().forEach(System.out::println);
    }
}
