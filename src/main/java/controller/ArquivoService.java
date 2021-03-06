package controller;

import dao.ArquivoDao;
import dao.UsuarioDao;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import model.Arquivo;
import model.Usuario;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author Souza
 */

@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
public class ArquivoService {

    @Context
    private UriInfo context;
    @EJB
    ArquivoDao arquivoDao;
    @EJB
    UsuarioDao usuarioDao;

    @GET
    @Path("/list")
    public List<Arquivo> getArquivos(@QueryParam("email") String email) {
        try {
            return arquivoDao.getArquivos(email);
        } catch (Exception e) {
            return null;
        }
    }
    
    @POST
    @Path("/new-file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(
            @FormDataParam("file") InputStream fluxoEntrada,
            @FormDataParam("file") FormDataContentDisposition metadados,
            @QueryParam("email")String email) throws Exception{
        
            Usuario u = usuarioDao.getUsuario(email);
            Arquivo arq = new Arquivo();
            arq.setNome(metadados.getFileName());
            arq.setUsuario(u);
                       
            arq.setConteudo(this.converterParaBinario(fluxoEntrada, metadados));
            arq.setTamanho(arq.getConteudo().length);
           
            arquivoDao.add(arq);
            
        return Response.status(200).entity("Arquivo enviado com sucesso").build();
    }
    
    /* O método abaixo traduz um baita malabarismo para salvar o conteúdo de inputStream para um array de bytes */
    public byte[] converterParaBinario(InputStream fluxoEntrada, FormDataContentDisposition meta) throws FileNotFoundException, IOException{
        
        ByteArrayOutputStream outArray = new ByteArrayOutputStream();
        int temp = 0;
        byte[] chunck = new byte[1024];
        
        do{
            outArray.write(chunck, 0, temp); 
            temp = fluxoEntrada.read(chunck, 0, 1024);/* O temp registra o indice do ultimo byte da iteracao lido em fluxoEntrada*/  
            
        }while(temp != -1); /* Para quando a leitura do fluxoEntrada chegar ao fim */
        
        byte[] blob = outArray.toByteArray(); 
        
        return blob;
    }
    
    @GET
    @Path("/file")
    @Produces("application/pdf")
    public Response download(@QueryParam("email") String email, @QueryParam("filename") String nomeArq) throws IOException{
        
        Arquivo arq = arquivoDao.getArquivo(email, nomeArq);
        // Converte o Arquivo (personalizado) em File (arquivo padronizado) atraves da conversao do blob
        File f = new File(arq.getNome());
        FileUtils.writeByteArrayToFile(f, arq.getConteudo());
        // Prepara o arquivo para envio o embutindo no response
        ResponseBuilder res = Response.ok((Object) f);
        res.header("Content-Disposition", "attachment; filename="+arq.getNome());
        return res.build();
    }
    
    @DELETE
    @Path("/file")
    public Response delete(@QueryParam("email") String email, @QueryParam("filename") String filename){
        try {
            Arquivo arq = arquivoDao.getArquivo(email, filename);
            arquivoDao.delete(arq);

            return Response.status(200).entity("Arquivo excluido com sucesso").build();
        }
        catch(Exception e){
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}