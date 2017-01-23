package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.Person;
import introsde.rest.ehealth.model.Diet;

import java.text.ParseException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class DietResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int nid;
    String type;
    int did;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public DietResource(UriInfo uriInfo, Request request,int nid, int did) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.nid = nid;
        this.did = did;
    }
 
    @PUT
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Diet putDiet(Diet diet) throws ParseException {
        System.out.println("--> Updating Diet... " +this.did);
        Diet.updateDiet(diet, nid, did);
        return diet;
    }
    
    @DELETE
    public void deleteDiet() {
        Diet a = Diet.getDietById(did);
        if (a == null)
            throw new RuntimeException("Delete: Diet with " + did
                    + " not found");
        Diet.removeDiet(a);
        System.out.println("Deletion Successful");
    }
}

