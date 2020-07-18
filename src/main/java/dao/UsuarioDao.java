package dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Usuario;

/**
 *
 * @author Souza
 */
@Stateless
public class UsuarioDao {
    
    @PersistenceContext
    EntityManager em;
   
    public void add(Usuario u){
        em.persist(u);
    }
    
    public void update(Usuario u){
        em.merge(u);
    }
    
    public void delete(Usuario u){
        em.remove(em.merge(u));
    }
    
    public Usuario getUsuario(String email){
        
        try{
        Query q = em.createQuery("select u from Usuario u where u.email = :email");
        q.setParameter("email", email);
        return (Usuario) q.getSingleResult();
        }catch(NullPointerException e){
            return null;
        }
    }
    
    public List<Usuario> getUsuarios(){
        Query q = em.createQuery("select u from Usuario u");
        return q.getResultList();
    }
}
