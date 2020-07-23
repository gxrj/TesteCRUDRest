package dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Arquivo;
import model.Usuario;

/**
 *
 * @author Souza
 */
@Stateless
public class ArquivoDao {
    
    @PersistenceContext
    EntityManager em;
    
        public void add(Arquivo a){
        em.persist(a);
    }
    
    public void update(Arquivo a){
        em.merge(a);
    }
    
    public void delete(Arquivo a){
        em.remove(em.merge(a));
    }
        
    private List<Arquivo> getArquivosDoUsuario(Usuario u){
        
        Query q = em.createQuery("select a from Arquivo a where exists"
                + "( select a from Arquivo a where a.usuario = :u)");
        q.setParameter("u", u);
        
        return q.getResultList();
    }
    
    public List<Arquivo> getArquivos(String email){
        Query q = em.createQuery("select u from Usuario u where u.email = :e");
        q.setParameter("e", email);

        return this.getArquivosDoUsuario( (Usuario) q.getSingleResult() );
    }

}
