package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Course implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4025197702318315157L;
	private String courseName;
	private Map<String, Module> modules;
	
	public Course(String courseName) {
		this.courseName = courseName;
		modules = new HashMap<String, Module>();
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String name) {
		courseName = name;
	}
	
	public void addModule(Module m) {
		modules.put(m.getModuleCode(), m);
	}
	
	public Module getModuleByCode(String code) {
		return modules.get(code);
	}
	
	public Collection<Module> getAllModulesOnCourse() {
		return modules.values();
	}
	
	@Override
	public String toString() {
		//a non-standard toString that simply returns the course name,
		//so as to assist in displaying courses correctly in a ComboBox<Course>
		return courseName;
	}
	
	public String actualToString() {
		return "Course:[courseName=" + courseName + ", modules=" + modules + "]";
	}
	
}
