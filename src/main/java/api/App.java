package api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Souza
 */
@javax.ws.rs.ApplicationPath("api")
public class App extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }
    /*O metodo getProperties() registra o multiparfeature no servlet*/
    public Map<String, Object> getProperties() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("jersey.config.server.provider.classnames",
                       "org.glassfish.jersey.media.multipart.MultiPartFeature");
        return properties;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(api.Filter.class);
        resources.add(controller.ArquivoService.class);   
        resources.add(controller.UsuarioService.class);
    }
    
}