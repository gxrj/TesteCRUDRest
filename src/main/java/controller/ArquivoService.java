package controller;

import dao.ArquivoDao;
import dao.UsuarioDao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Arquivo;
import model.Usuario;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author Souza
 */
@Path("/docs")
public class ArquivoService {

    @Context
    private UriInfo context;
    @EJB
    ArquivoDao arquivoDao;
    @EJB
    UsuarioDao usuarioDao;

    @Path("/{email}")
    public List<Arquivo> getArquivos(@QueryParam("email") String email) {
        try {
            return arquivoDao.getArquivos(email);
        } catch (Exception e) {
            return null;
        }
    }
    
    @POST
    @Path("/{email}/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(
            @FormDataParam("file") InputStream in,
            @FormDataParam("file") FormDataContentDisposition metadados,
            @PathParam("email")String email) throws Exception{
        
            
            Usuario u = usuarioDao.getUsuario(email);
            Arquivo arq = new Arquivo();
            arq.setNome(metadados.getFileName());
            arq.setUsuario(u);
            
            
            arq.setConteudo(this.parse(in, metadados));
            
            u.getArquivos().add(arq);
           
            arquivoDao.add(arq);
            usuarioDao.update(u);
            
        return Response.status(200).entity("Arquivo enviado com sucesso").build();
    }
    
    public byte[] parse(InputStream in, FormDataContentDisposition meta) throws FileNotFoundException{
        
        byte[] blob = new byte[(int) meta.getSize()];
       
        try{
            in.read(blob);
            in.close();
        }catch(IOException ex){
            ex.printStackTrace();
            return null;
        }
        return blob;
    }
}
