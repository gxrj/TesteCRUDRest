package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author Souza
 */
@Entity
public class Usuario implements Serializable{
    
    private String nome;
    @Id
    private String email;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "usuario")
    @JoinColumn(name = "usuario_email")
    private List<Arquivo> arquivos;

    
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

    public List<Arquivo> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<Arquivo> arquivos) {
        this.arquivos = arquivos;
    }  
}
