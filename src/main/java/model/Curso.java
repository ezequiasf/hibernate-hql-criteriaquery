/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import constants.CursosFacul;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author zectr
 */
@Entity

@Table(name="Curso_Faculdade")
public class Curso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tipo;
    
    public Curso() {
    }

    public Curso(CursosFacul tipo) {
        this.tipo = tipo.getCurso();
    }

    public int getId() {
        return id;
    }  
    
    public String getCurso() {
        return tipo;
    }
    
    public Curso getInstancia (){
        return this;
    }

    public void setCurso(CursosFacul tipo) {
        this.tipo = tipo.getCurso();
    }
         
}
