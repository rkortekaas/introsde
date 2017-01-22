package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.Person;
import introsde.rest.ehealth.model.Goal;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class GoalResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;
    String type;
    int aid;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public GoalResource(UriInfo uriInfo, Request request,int id, int aid) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.aid = aid;
    }
 
    @PUT
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Goal putGoal(Goal goal) throws ParseException {
        System.out.println("--> Updating Goal... " +this.aid);
        Goal.updateGoal(goal, id, aid);
        return goal;
    }
    
    @DELETE
    public void deleteGoal() {
        Goal a = Goal.getGoalById(aid);
        if (a == null)
            throw new RuntimeException("Delete: Goal with " + aid
                    + " not found");
        Goal.removeGoal(a);
    }
}

