package controller;

import dao.ArquivoDao;
import dao.UsuarioDao;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
@Produces(MediaType.APPLICATION_JSON)
public class ArquivoService {

    @Context
    private UriInfo context;
    @EJB
    ArquivoDao arquivoDao;
    @EJB
    UsuarioDao usuarioDao;

    @GET
    @Path("/{email}")
    public List<Arquivo> getArquivos(@QueryParam("email") String email) {
        try {
            return arquivoDao.getArquivos(email);
        } catch (Exception e) {
            return null;
        }
    }
    
    @POST
    @Path("/{email}/doc")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    
    public Response upload(
            @FormDataParam("file") InputStream fluxoEntrada,
            @FormDataParam("file") FormDataContentDisposition metadados,
            @PathParam("email")String email) throws Exception{
        
            Usuario u = usuarioDao.getUsuario(email);
            Arquivo arq = new Arquivo();
            arq.setNome(metadados.getFileName());
            arq.setUsuario(u);
                       
            arq.setConteudo(this.parse(fluxoEntrada, metadados));
            arq.setTamanho(arq.getConteudo().length);
           
            arquivoDao.add(arq);
            usuarioDao.update(u);
            
        return Response.status(200).entity("Arquivo enviado com sucesso").build();
    }
    
    /* O método abaixo traduz um puta malabarismo para salvar o conteúdo de inputStream para um array de bytes */
    public byte[] parse(InputStream fluxoEntrada, FormDataContentDisposition meta) throws FileNotFoundException, IOException{
        
        ByteArrayOutputStream outArray = new ByteArrayOutputStream();
        int temp = 0;
        byte[] blob = new byte[1024];
        
        do{
            outArray.write(blob, 0, temp); 
            temp = fluxoEntrada.read(blob, 0, 1024);/* O temp registra o indice do ultimo byte da iteracao lido em fluxoEntrada*/  
            
        }while(temp != -1); /* Para quando a leitura do fluxoEntrada chegar ao fim */
        
        blob = outArray.toByteArray(); /* blob esta com novo tamanho e valores */
        
        return blob;
    }
}