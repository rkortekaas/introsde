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
@Table(name="Nutrition")
@NamedQuery(name="Nutrition.findAll", query="SELECT n FROM Nutrition n")
@XmlRootElement
public class Nutrition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_nutrition")
	@TableGenerator(name="sqlite_nutrition", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="Nutrition")
	@Column(name="idNutrition")
	private int idNutrition;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date")
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;

	public int getIdNutrition() {
		return idNutrition;
	}

	public void setIdNutrition(int idNutrition) {
		this.idNutrition = idNutrition;
	}

	public String getDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(this.date);
	}
	
    public void setDate(String da) throws ParseException{
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = format.parse(da);
        this.date = date;
    }
    
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	// db operations
	
    public static Nutrition saveNutrition(Nutrition n, int id) {
    	Person p = Person.getPersonById(id);
    	n.setPerson(p);
    	
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(n);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return n;
    } 

    public static Nutrition updateNutrition(Nutrition n, int id, int nid) {
        Person p = Person.getPersonById(id);
        n.setPerson(p);
        n.setIdNutrition(nid);
    	
    	EntityManager em = LifeCoachDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        n=em.merge(n);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return n;
    }

    public static void removeNutrition(Nutrition n) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        n=em.merge(n);
        em.remove(n);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }

	public static Nutrition getNutritionById(int nutritionId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        Nutrition n = em.find(Nutrition.class, nutritionId);
        LifeCoachDao.instance.closeConnections(em);
        return n;
    }

    public static List<Nutrition> getAll(int personId) {
    	EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<Nutrition> list = em.createNamedQuery("Nutrition.findAll", Nutrition.class)
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
