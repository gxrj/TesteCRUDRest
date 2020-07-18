package controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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
    private Gson gson = new Gson();
    
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
    public Response post(String userJSON){
       
       try{ 
            Usuario u = gson.fromJson(userJSON, Usuario.class);
            usuarioDao.add(u);
       }catch(JsonSyntaxException e){
           return Response.status(500).entity(e.getMessage()).build();
       }
        
        return Response.status(201).entity("Usuário criado com sucesso").build();
    }

    @PUT
    public Response put(String userJSON){
        try{
            Usuario u = gson.fromJson(userJSON, Usuario.class);
            usuarioDao.update(u);
        }catch(JsonSyntaxException e){
            return Response.status(500).entity(e.getMessage()).build();
        }
        return Response.status(200).entity("Usuário atualizado com sucesso").build();
    }
    
    @DELETE
    @Path("{email}")
    public Response delete(@PathParam("email") String email){
        try{
            Usuario u = usuarioDao.getUsuario(email);
            usuarioDao.delete(u);
        }catch(JsonSyntaxException e){
            return Response.status(500).entity(e.getMessage()).build();
        }
        return Response.status(200).entity("Usuário deletado com sucesso").build();
    }
}
