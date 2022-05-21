package service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/recource")

public class Config extends Application{
    @Override
    public Set<Class<?>> getClasses(){
        HashSet poviders = new HashSet<Class<?>>();
        poviders.add(TestService.class);
        return poviders;
    }
}
