package introsde.rest.ehealth.model;

import introsde.rest.ehealth.dao.LifeCoachDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
@Table(name="Diet")
@NamedQuery(name="Diet.findAll", query="SELECT d FROM Diet d")
@XmlRootElement
public class Diet implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator="sqlite_diet")
	@TableGenerator(name="sqlite_diet", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="Diet")
	@Column(name="idDiet")
	private int idDiet;
	
	@Column(name="name")
	private String name;
	
	@Column(name="calories")
	private int calories;
	
	@Column(name="quantity")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "idNutrition", referencedColumnName = "idNutrition")
	private Nutrition nutrition;

	public int getIdDiet() {
		return idDiet;
	}

	public void setIdDiet(int idDiet) {
		this.idDiet = idDiet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Nutrition getNutrition() {
		return nutrition;
	}

	public void setNutrition(Nutrition nutrition) {
		this.nutrition = nutrition;
	}
	
	// db operations
	
	public static Diet saveDiet(Diet d, int id) {
		Nutrition n = Nutrition.getNutritionById(id);
        d.setNutrition(n);
    	
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(d);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return d;
    } 

    public static Diet updateDiet(Diet d, int id, int did) {
        Nutrition n = Nutrition.getNutritionById(id);
        d.setNutrition(n);
        d.setIdDiet(did);
    	
    	EntityManager em = LifeCoachDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        d=em.merge(d);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return d;
    }

    public static void removeDiet(Diet d) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        d=em.merge(d);
        em.remove(d);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }
    
	public static Diet getDietById(int dietId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        Diet d = em.find(Diet.class, dietId);
        LifeCoachDao.instance.closeConnections(em);
        return d;
    }
    
    public static List<Diet> getAll(int nutritionId) {
    	EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<Diet> list = em.createNamedQuery("Diet.findAll", Diet.class)
            .getResultList();
        LifeCoachDao.instance.closeConnections(em);
        for (int index = list.size()-1 ; index >= 0 ; index--){
        	if ((list.get(index).getNutrition().getIdNutrition()!=nutritionId) ){
        		list.remove(index);
        	}
        }
        return list;
    }
	
}
