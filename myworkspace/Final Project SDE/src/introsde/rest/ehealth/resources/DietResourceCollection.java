package introsde.rest.ehealth.resources;
import introsde.rest.ehealth.model.Diet;
import introsde.rest.ehealth.model.Person;

import java.io.IOException;
import java.util.List;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application

public class DietResourceCollection {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    // nutrition id
    int nid;
    
    public DietResourceCollection(UriInfo uriInfo, Request request, int nid) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.nid = nid;
    }
    
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<Diet> getDiets() {
    	List<Diet> diets = null;
        	System.out.println("Getting all diets");
            diets = Diet.getAll(nid);        	
        return diets;
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    @Consumes({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public Diet newDiet(Diet diet) throws IOException {
        System.out.println("Creating new diet...");            
        return Diet.saveDiet(diet, nid);
    }

}