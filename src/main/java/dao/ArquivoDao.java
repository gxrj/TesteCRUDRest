package dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Arquivo;

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
        
    public List<Arquivo> getArquivos(String email){
        Query q = em.createQuery("select a from Arquivo a where a.usuario.email = :email");
        q.setParameter("email", email);
        return q.getResultList();
    }
}
