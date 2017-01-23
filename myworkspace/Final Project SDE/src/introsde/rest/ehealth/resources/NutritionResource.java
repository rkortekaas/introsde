package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.Person;
import introsde.rest.ehealth.model.Nutrition;
import introsde.rest.ehealth.model.HealthMeasureHistory;
import introsde.rest.ehealth.model.MeasureDefinition;

import java.text.ParseException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless // only used if the the application is deployed in n Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class NutritionResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    // personId
    int id;
    // nutritionId
    int nid;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public NutritionResource(UriInfo uriInfo, Request request,int id, int nid) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.nid = nid;
    }
 
    @PUT
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Nutrition putNutrition(Nutrition nutrition) throws ParseException {
        System.out.println("--> Updating Nutrition... " +this.nid);
        Nutrition.updateNutrition(nutrition, id, nid);
        return nutrition;
    }
    
    @DELETE
    public void deleteNutrition() {
        Nutrition n = Nutrition.getNutritionById(nid);
        if (n == null)
            throw new RuntimeException("Delete: Nutrition with " + nid
                    + " not found");
        Nutrition.removeNutrition(n);
    }

    @Path("/diet")
    public DietResourceCollection getDietsNutrition() {
       return new DietResourceCollection(uriInfo, request, nid);
    }

     @Path("/diet/{did}")
     public DietResource getDiet(@PathParam("did") int did) {
        return new DietResource(uriInfo, request, nid, did);
     }
}

