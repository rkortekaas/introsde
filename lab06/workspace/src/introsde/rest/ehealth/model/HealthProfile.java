package introsde.rest.ehealth.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@XmlRootElement(name="healthprofile")
@XmlType(propOrder = { "weight", "height", "BMI", "history" })
@XmlAccessorType(XmlAccessType.FIELD)
public class HealthProfile {
	private double weight; // in kg
	private double height; // in m
	
//	List<HealthUpdates> history = new ArrayList<>();
	
	@XmlElement(name="history")
	public List<Date> history() {
		Date date = new Date();
		System.out.println("Laatste update: " + date.toString());
		List<Date> mylist = new ArrayList<Date>();
		mylist.add(date);
		return mylist;
	}
	
	public HealthProfile(double weight, double height) {
		this.weight = weight;
		this.height = height;
	}

	public HealthProfile() {
		this.weight = 85.5;
		this.height = 1.72;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	@XmlElement(name="bmi")
	public double getBMI() {
		return this.weight/(Math.pow(this.height, 2));
	}
	
	public String toString() {
		return "{"+this.height+","+this.weight+","+this.getBMI()+","+"}";
	}
	
}