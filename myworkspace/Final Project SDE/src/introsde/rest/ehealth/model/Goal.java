package introsde.rest.ehealth.model;

import introsde.rest.ehealth.dao.LifeCoachDao;
import introsde.rest.ehealth.model.Person;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="Goal")
@NamedQuery(name="Goal.findAll", query="SELECT g FROM Goal g")
@XmlRootElement
public class Goal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_goal")
	@TableGenerator(name="sqlite_goal", table="sqlite_sequence",
		pkColumnName="name", valueColumnName="seq",
		pkColumnValue="Goal")
	@Column(name="\"idGoal\"")
	private int idGoal;
	
	@Column(name="name")
	private String name;
	
	@Column(name="type")
	private String type;
	
	@Column(name="description")
	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="deadline")
	private Date deadline;

	// notice that we haven't included g reference to the history in Person
	// this means that we don't have to make this attribute XmlTransient
	
	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;

	public Goal() {
	}

	public int getIdGoal() {
		return this.idGoal;
	}

	public void setIdGoal(int idGoal) {
		this.idGoal = idGoal;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDeadline() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(this.deadline);
	}
	
    public void setDeadline(String da) throws ParseException{
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = format.parse(da);
        this.deadline = date;
    }

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Person getPerson() {
	    return person;
	}

	public void setPerson(Person param) {
	    this.person = param;
	}

	// database operations
	
    public static Goal saveGoal(Goal g, int id) {
    	Person p = Person.getPersonById(id);
    	g.setPerson(p);
    	
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(g);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return g;
    } 

    public static Goal updateGoal(Goal g, int id, int gid) {
        Person p = Person.getPersonById(id);
        g.setPerson(p);
        g.setIdGoal(gid);
    	
    	EntityManager em = LifeCoachDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        g=em.merge(g);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return g;
    }

    public static void removeGoal(Goal g) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        g=em.merge(g);
        em.remove(g);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }
    
	public static Goal getGoalById(int goalId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        Goal g = em.find(Goal.class, goalId);
        LifeCoachDao.instance.closeConnections(em);
        return g;
    }
    
    public static List<Goal> getAll(int personId) {
    	EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<Goal> list = em.createNamedQuery("Goal.findAll", Goal.class)
            .getResultList();
        LifeCoachDao.instance.closeConnections(em);
        for (int index = list.size()-1 ; index >= 0 ; index--){
        	if ((list.get(index).getPerson().getIdPerson()!=personId) ){
        		list.remove(index);
        	}
        }
        return list;
    }

}