package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Souza
 */
@Entity
@Table(name="Usuario")
public class Usuario implements Serializable{
    
    private String nome;
    @Id
    private String email;
    
    public Usuario(){  }
    
    public Usuario(String email, String nome){
        this.email = email;
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
