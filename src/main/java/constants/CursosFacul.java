/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

/**
 *
 * @author zectr
 */
public enum CursosFacul {
    
    GEOGRAFIA("Geografia"), 
    MATEMATICA("Matemática"), 
    FISICA("Física"), 
    LETRAS("Letras"), 
    COMPUTACAO("Computação");
    
    private final String curso;
    
    CursosFacul(String c){
        curso = c;
    }

    public String getCurso(){
        return curso;
    }
    
}
