package controller;

import dao.UsuarioDao;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Usuario;

/**
 *
 * @author Souza
 */

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioService {
    
    @Context
    private UriInfo context;
    
    @EJB
    UsuarioDao usuarioDao;
    
    @GET
    public List<Usuario> getUsuarios(){
        List <Usuario> list = usuarioDao.getUsuarios();
    
        return list;
    }
    
    @GET
    @Path("/id")
    public Usuario getUsuario(@QueryParam("email")String email){
        Usuario u = usuarioDao.getUsuario(email);
        
        return u;
    } 
    
    @POST
    @Path("/{nome}/{email}")
    public Response post(@PathParam("nome") String nome, @PathParam("email") String email){
        Usuario u = new Usuario();
        u.setEmail(email);
        u.setNome(nome);
        
        usuarioDao.add(u);
        
        return Response.status(201).entity("Usuário criado com sucesso").build();
    }
    
    @PUT
    public void put(){
        
    }
    
    @DELETE
    public void delete(){
        
    }
}
