package api;

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

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(controller.UsuarioService.class);
    }
    
}