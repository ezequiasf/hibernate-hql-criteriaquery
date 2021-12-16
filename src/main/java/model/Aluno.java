/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author zectr
 */
@Entity

@Table(name="Alunos_Faculdade")
public class Aluno implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String nome;
    private int idade;
    @OneToOne
    private Curso curso;
    /*
        Quando se faz este procedimento (Adicionar uma foreign key)
        Estou causando dependência do aluno para com o curso(Curso independente).
        Se eu apagar o aluno, o curso continua "vivo".
    */
    public Aluno() {
    }

    public Aluno(String nome, int idade, Curso curso) {
        this.nome = nome;
        this.idade = idade;
        this.curso = curso;
    }

    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    //------------------------------------------
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Aluno:" + nome + ", Curso:" + curso.getCurso();
    }
     
}
