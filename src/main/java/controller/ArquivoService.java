package controller;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Souza
 */

@Path("/docs")
public class ArquivoService {
    
    @Context
    private UriInfo context;
    
    
    public Response upload(){
        
        return Response.status(200).entity("Arquivo enviado com sucesso").build();
    }
}
